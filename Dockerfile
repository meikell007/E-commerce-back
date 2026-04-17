# ============================================================
# Dockerfile — E-commerce Back (Spring Boot 4 / Java 21)
# ============================================================

# ---------- Etapa 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar descriptor de dependencias primero (cache de capas)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---------- Etapa 2: Runtime ----------
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Usuario no-root por seguridad
RUN groupadd --system appgroup && useradd --system --gid appgroup appuser

# Copiar el JAR generado
COPY --from=builder /app/target/*.jar app.jar

RUN chown appuser:appgroup app.jar
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
