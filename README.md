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
dsrm.auth.jwt.ExpirationMs = # login token expiration time in miliseconds
dsrm.auth.jwt.RefreshExpirationMs = # refresh token expiration time in miliseconds
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

### Test data
To load initial data from `data.sql` for testing you should add
`spring.jpa.defer-datasource-initialization=true` and `spring.sql.init.mode=always`
to application.properties

### Authentication properties
There are two authentication properties that can be overridden:
 - `dsrm.auth.jwt.ExpirationMs`
 - `dsrm.auth.jwt.RefreshExpirationMs`

`dsrm.auth.jwt.ExpirationMs` specifies login token expiration time in miliseconds. Default: 3600000
`dsrm.auth.jwt.RefreshExpirationMs` specifies refresh token expiration time in miliseconds. Default: 86400000


