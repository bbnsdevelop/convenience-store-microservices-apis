# Spring Boot Microservices


# This repo was concept to learn about microservices architecture and it uses:
- Java 17
- apache-maven-3.9.4 or currently version
- Docker and docker-compose.
- mongo
- mysql
- elasticsearch 
- kibana
- logstash
- kafka
- rabbitMQ
- Zipkin
- Circuit Break (Resilience4J)
- Git version control
- API Gateway
- Vault
- Service discovery
- Centralized configuration



# Step 1:
  - open a termial, navigate to folder composes
  - run a command docker-compose up -d
  - after you run this command the containers (mongo, mongo-express, mysql_8, elasticsearch, kibana, logstash) will stay UP


# Swagger url API's
- product service: http://localhost:8081/swagger-ui/index.html
- order service: http://localhost:8082/swagger-ui/index.html
- inventory service: http://localhost:8083/swagger-ui/index.html


# Discovery server
- http://localhost:8761/

* Discovery exposed in gateway 
    - http://localhost:8080/eureka/web

# API Gateway
- http://localhost:8080/

  - Services expose on API gateway
    * routes:
      - id: product-service
        uri: lb://product-service
        predicates:
        - Path=/api/products/v1
        - Method=GET,POST
      - id: order-service
        uri: lb://order-service
        predicates:
        - Path=/api/orders/v1,/api/orders/v1/{id}
        - Method=GET,POST

- example of url: http://localhost:8080/api/orders/v1


# log - elasticsearch
- http://localhost:5601/app/


# Security
- Keycloak url: http://localhost:8181/
- client name: convinience-cloud-client
- client url issuer: http://localhost:8181/realms/convinience-cloud-client
- client url Token: http://localhost:8181/realms/convinience-cloud-client/protocol/openid-connect/token

# Databases

- mysql:
    To acess container mysql use this command: docker exec -it mysql-8 mysql -u root -p

- mongodb:
    To acess container mongoDB use this command: docker exec -it mongoDB bash
    inside the container use: mongosh -u admin -p '123asd' --authenticationDatabase admin

- mongo-express: http://localhost:8099/
        - user: dev
        - pass: dev