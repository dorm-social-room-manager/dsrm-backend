# Dorm social rooms manager (backend)

This repository is for backend part of Dorm Social Rooms Manager.
Made using Java and Spring Boot

### Requirements

To run this repository you need:
 - Git
 - MariaDB server
 - Docker

### Downloading

```bash
git clone https://github.com/dorm-social-room-manager/dsrm-backend.git
cd dsrm-backend
```

### Necessary configuration
In application.properties we have to specify:
```
spring.datasource.url = # database url
spring.datasource.username = # database username
spring.datasource.password = # database password
spring.datasource.driver-class-name = org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto = create-drop
spring.security.user.name = # username
spring.security.user.password = # user password
```

### Building application
```bash
./mvnw install
```

### Running tests

```bash
./mvnw test
```

**NOTE:** Docker is required to run tests

### Running application

```bash
./mvnw spring-boot:run
```


**NOTE:** Running through Maven may require to set `JAVA_HOME` system variable to your JDK location






