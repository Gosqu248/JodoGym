FROM openjdk:21-jdk-bullseye

WORKDIR /app

# Add a volume for temporary files
VOLUME /tmp

COPY mvnw pom.xml ./
COPY .mvn ./.mvn

# Download dependencies (will be cached if no changes)
RUN ./mvnw dependency:go-offline -B || true

# Copy source code
COPY src ./src

RUN chmod +x ./mvnw

# Build the application with Maven cache
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests

# Extract JAR file name using find to handle version numbers
RUN find target -name "*.jar" -not -name "*sources.jar" -not -name "*javadoc.jar" -exec cp {} app.jar \;

ENV SPRING_PROFILES_ACTIVE=prod

# Run Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
