package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;


    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);


        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);

            insertBatch(user.getRoles(), newKey);


            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        } else {
            /*Фиг его знает как обновлять роли
             * 1. Что делать когда передается одна роль а у него их три?
             * Значит так , сначало удалю все роли(есть нету -пофиг) , затем заново вставка
             * */
            jdbcTemplate.update("delete from user_roles where  user_id =?", user.getId());
            insertBatch(user.getRoles(), user.getId());
        }
        ;
        return user;
    }

    void insertBatch(Set<Role> roles, Number newKey) {
        String sql = "INSERT into user_roles (user_id, role) VALUES (?,?) ";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            List<Role> listRole = new ArrayList<>(roles);

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Role roles = listRole.get(i);
                ps.setInt(1, newKey.intValue());
                ps.setString(2, roles.name());
            }

            @Override
            public int getBatchSize() {
                return listRole.size();
            }

        });
    }

    void deleteBatch(Number newKey) {
        String sql = "delete from user_roles where  user_id =? ";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, newKey.intValue());
            }

            @Override
            public int getBatchSize() {
                return 0;
            }

        });
    }


    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);

      /* выбирает роли и сразу добавляет в users. Работает - но перенесем в getRoles
       jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?", (rs, rowNum) -> {
            users.stream()
                    .filter(user -> {
                        try {
                            return user.getId() == rs.getInt("user_id");
                        } catch (SQLException e) {
                           return false;
                        }
                    })
                    .forEach(user -> {
                        try {
                            if ( user.getRoles() == null)
                                user.setRoles(EnumSet.of(Role.valueOf(rs.getString("role"))));
                            else
                            user.getRoles().add((Role.valueOf(rs.getString("role")) ));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });


            return null;
        }, id);
*/
        User user = DataAccessUtils.singleResult(users);
        if (user != null) {
            user.setRoles(getRoles(id));
        }

        return user;
    }

    Set<Role> getRoles(int id) {

//        return  new HashSet<>(( jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?",(rs, rowNum) -> {
//            return Role.valueOf(rs.getString("role"));
//        },id)));

        return jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?", (rs, rowNum) ->
                        Role.valueOf(rs.getString("role"))
                , id).stream().collect(Collectors.toSet());

    }


    Map<Integer, Set<Role>> getAllRoles() {
        Map<Integer, Set<Role>> rolesWithUsersID = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles ", (rs, rowNum) -> {
                    rolesWithUsersID.computeIfAbsent(rs.getInt("user_id"), k -> new HashSet<>()
                    ).add(Role.valueOf(rs.getString("role")));
                    return null;
                }
        );
        return rolesWithUsersID;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        if (user != null) {
            user.setRoles(getRoles(user.getId()));
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> query = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, Set<Role>> allRoles = getAllRoles();
        query.forEach(user -> user.setRoles(allRoles.get(user.getId())));
        return query;

    }
}