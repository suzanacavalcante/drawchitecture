# Estágio 1: Builder (Compilação)
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw
COPY . .
RUN ./mvnw clean package -DskipTests

# Estágio 2: Runtime (Apenas o Java para rodar o JAR)
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=builder /app/target/*.jar app.jar

# Cria a pasta de saída para os PNGs baixados
RUN mkdir -p /app/outputs && chmod 777 /app/outputs

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]