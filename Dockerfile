# Estágio 1: Build (Mantido para garantir build limpo)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY api/pom.xml api/
COPY tests/pom.xml tests/
RUN mvn dependency:go-offline -B
COPY api/src api/src
COPY tests/src tests/src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia o JAR inicial (caso não haja volume montado)
COPY --from=build /app/api/target/*.jar /app/app.jar

# Variável para facilitar a troca do caminho do JAR se necessário
ENV JAR_PATH=/app/app.jar

EXPOSE 8080

# O script verifica se existe um JAR no volume montado (/app/target) 
# Se existir, usa ele (para Hot Reload), senão usa o interno.
ENTRYPOINT ["sh", "-c", "if [ -f /app/target/api-1.0-SNAPSHOT.jar ]; then java ${JAVA_OPTS} -jar /app/target/api-1.0-SNAPSHOT.jar; else java ${JAVA_OPTS} -jar /app/app.jar; fi"]
