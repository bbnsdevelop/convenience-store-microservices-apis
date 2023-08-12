# Spring Boot Microservices


To run this project you will need this technologies:
Java 17
apache-maven-3.9.4 or currently version
Docker and docker-compose.



# Step 1:
  - open a termial, navigate to folder composes
  - run a command docker-compose up -d
  - after you run this command the containers (mongo, mongo-express, mysql_8, elasticsearch, kibana, logstash) will stay UP


# Swagger url API's
- product service: http://localhost:8080/swagger-ui/index.html
- order service: http://localhost:8081/swagger-ui/index.html


# log - elasticsearch
- http://localhost:5601/app/


# Databases

- mysql:
    To acess container mysql use this command: docker exec -it mysql-8 mysql -u root -p

- mongodb:
    To acess container mongoDB use this command: docker exec -it mongoDB bash
    inside the container use: mongosh -u admin -p '123asd' --authenticationDatabase admin

- mongo-express: http://localhost:8099/
        - user: dev
        - pass: dev