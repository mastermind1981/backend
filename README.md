# backend [![Build Status](https://travis-ci.org/owale/backend.svg?branch=master)](https://travis-ci.org/owale/backend)
> crossover task, backend implementation with java, spring, rest, hibernate and mysql

**run**
```Mysql
mysql -u root -p -e "CREATE SCHEMA crossover"
mysql -u root -p < ./configuration/init.sql
```
```bash
mvn clean install
mvn test
mvn tomcat7:run
# open your browser @ http://localhost:8080/customer/
```