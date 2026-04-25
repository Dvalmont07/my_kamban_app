# Stage 1: Build (Maintained to ensure a clean build)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY api/pom.xml api/
COPY tests/pom.xml tests/
RUN mvn dependency:go-offline -B
COPY api/src api/src
COPY tests/src tests/src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy initial JAR (in case no volume is mounted)
COPY --from=build /app/api/target/*.jar /app/app.jar

# Variable to facilitate changing the JAR path if necessary
ENV JAR_PATH=/app/app.jar

EXPOSE 8080

# The script checks if a JAR exists in the mounted volume (/app/target)
# If it exists, use it (for Hot Reload), otherwise use the internal one.
ENTRYPOINT ["sh", "-c", "if [ -f /app/target/api-1.0-SNAPSHOT.jar ]; then java ${JAVA_OPTS} -jar /app/target/api-1.0-SNAPSHOT.jar; else java ${JAVA_OPTS} -jar /app/app.jar; fi"]
