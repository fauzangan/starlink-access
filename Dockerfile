FROM maven:3.8.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-focal

WORKDIR /app

COPY --from=builder /app/target/starlink-access-0.0.1-SNAPSHOT.jar application.jar

ENV SPRING_PROFILES_ACTIVE=production

HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]