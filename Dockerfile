# Maven
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Mermaid CLI
RUN apk add --no-cache graphviz ttf-freefont

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]