# stg-1 : Build the application 
FROM openjdk:21-jdk AS build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

# stg-2: Package the application
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]
