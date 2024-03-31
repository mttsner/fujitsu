# Delivery fee calculation API
The api is available at `/deliveryfee`.  
OpenAPI v3 documentation is available at `/v3/api-docs`.  
SwaggerUI documentation is available at `/swagger-ui/index.html`.

The value in euros of fees applied can be configured in [fees.json](./src/main/resources/fees.json).

## Development

After starting the application it is accessible under `localhost:8080`.  
H2 database web console is accessible under `localhost:8080/h2-console`.  

## Build

The application can be built using the following command:

```
gradlew clean build
```

Start your application with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./build/libs/fujitsu-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as
environment variable when running the container.

```
gradlew bootBuildImage --imageName=mttsner/fujitsu
```