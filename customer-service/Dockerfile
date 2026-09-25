FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY . .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -pl customer-service -am -DskipTests clean package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/customer-service/target/*.jar app.jar
EXPOSE 8886
ENTRYPOINT ["java","-jar","app.jar"]
