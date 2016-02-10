# backend [![Build Status](https://travis-ci.org/owale/backend.svg?branch=master)](https://travis-ci.org/owale/backend)
> crossover task, backend implementation with java, spring, rest, hibernate and mysql

**run**
```Mysql
mysql -u root -p -e "CREATE SCHEMA crossover"
mysql -u root -p < ./configuration/init.sql
```
```bash
mvn clean install test tomcat7:run
# open front end application now
```

**Design:**
 * spring mvc as a top layer for manipulation CRUD operation for  a resource _Customer_, _Product_ or _SalesOrder_.
 * Spring controller pass operation to resource Service.
 * Resource Service interact with ORM using hibernate based on Generic Class _hibnerateD o_, responsible for making a Generic operation on DB.
 * DB is Mysql with minimum configuration :
  * create schema with name crossover
  * run script `configuration/init.sql` which only create access user with name `demo` and no password.
  
**DataBase Design**
 * Product Table
 * Customer Table
 * SalesOrder Table (connect customer with his orderLines)
 * OrderLine Table  (contain Product, Quantity and SalesOrder reference)
 
 **ER Digram is attached in configuration/er-diagram**
  * generated with workBench and use `connect to models` notation in alternative words use `1` or `∞` to represent relations.
  
 **Deployment**
  * fellow same step as in _run_
    
 