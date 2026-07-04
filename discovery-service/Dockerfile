# syntax=docker/dockerfile:1

# ---------- Stage 1: build (JDK 21 + Maven live here, not in the final image) ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy the whole multi-module project: the build needs the parent pom.xml too,
# not just this service's folder.
COPY . .

# Build ONLY this module. The --mount cache keeps Maven's ~/.m2 between builds
# so dependencies download once, not every time.
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -pl discovery-service -am -DskipTests clean package

# ---------- Stage 2: runtime (slim JRE only) ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy just the built jar out of the build stage. Glob avoids hardcoding the version.
COPY --from=build /workspace/discovery-service/target/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java","-jar","app.jar"]
