# ---- Stage 1: Build ----
FROM gradle:jdk21-alpine AS builder
WORKDIR /app

COPY gradlew ./
COPY gradle/ ./gradle/
COPY build.gradle ./
COPY gradle.properties ./
COPY settings.gradle* ./
RUN chmod +x ./gradlew

# Run the dependencies task to download all dependencies
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the source code
COPY src ./src

# Build the application, skipping tests.
RUN ./gradlew bootJar --no-daemon -x test

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]