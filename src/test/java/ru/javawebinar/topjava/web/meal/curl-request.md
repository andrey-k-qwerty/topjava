### Get All
#### curl -v -H "Accept: application/json" http://localhost:8080/topjava/rest/meals
### Get by id
#### curl -v -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/100002
### filter by 2 dates
#### curl -v -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/filter/2?startDate=2015-05-31T10:00:00\&endDate=2015-05-31T19:00:00
### filter by 4 dates
#### curl -v -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/filter/4?startDate=2015-05-31\&endDate=2015-05-31\&startTime=01:00\&endTime=22:00
### new meal
#### curl -v  -H "Content-Type: application/json" -d '{"dateTime": "2019-05-31T10:00:00", "description": "Плотный завтрак", "calories": 1500}' http://localhost:8080/topjava/rest/meals
### Update meal
#### curl -v -X "PUT" -H "Content-Type: application/json" -d '{ "dateTime": "2015-05-30T11:00:00", "description": "breacfast", "calories": 1500}' http://localhost:8080/topjava/rest/meals/100002
### Delete meal
####  curl -X "DELETE" http://localhost:8080/topjava/rest/meals/100012