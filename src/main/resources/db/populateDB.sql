DELETE
FROM user_roles;
DELETE
from meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);


DO $$
  declare
    u integer;
    a integer;
  begin
    SELECT users.id into u FROM users where name = 'User';
    SELECT users.id into a FROM users where name = 'Admin';
    INSERT INTO meals (user_id, datetime, description, calories)
    VALUES (u, timestamp '2015-05-30 10:00', 'Завтрак', 500)
        ,
           (u, timestamp '2015-05-30 13:00', 'Обед', 1000)
        ,
           (u, timestamp '2015-05-30 20:00', 'Ужин', 500)
        ,
           (u, timestamp '2015-05-31 10:00', 'Завтрак', 1000)
        ,
           (u, timestamp '2015-05-31 13:00', 'Обед', 500)
        ,
           (u, timestamp '2015-05-31 20:00', 'Ужин', 510)
        ,
           (a, timestamp '2015-07-01 14:00', 'Админ ланч', 510)
        ,
           (a, timestamp '2015-07-01 21:00', 'Админ ужин', 1500);
    RAISE NOTICE 'Insert - done ';

  end ;
  $$ LANGUAGE plpgsql;


--   new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
-- new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
-- new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
-- new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
-- new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
-- new Meal(LocalDateTime.of(2015, Month.MAY,
-- save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510), ADMIN_ID);
-- save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500), ADMIN_ID);
