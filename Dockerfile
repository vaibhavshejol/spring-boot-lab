# Build stage
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Run stage
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]

# # Run stage & Save Source Code Also
# FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
# WORKDIR /app
# COPY --from=build /app .
# EXPOSE 9090
# ENTRYPOINT ["java", "-jar", "build/libs/springbootlab-0.0.1-SNAPSHOT.jar"]