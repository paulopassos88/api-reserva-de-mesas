# Dockerfile multiestágio para Spring Boot (Java 17)
# Estágio de build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e resolve dependências antecipadamente para melhor cache
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Copia o código-fonte e realiza o build
COPY src ./src
RUN mvn -B -q -DskipTests package

# Estágio de execução (runtime)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o fat jar do estágio de build
COPY --from=build /app/target/*.jar /app/app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Opções da JVM podem ser fornecidas via variável de ambiente JAVA_OPTS
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Healthcheck (opcional; funciona com docker run; no compose pode haver configuração própria)
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
