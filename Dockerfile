# Use a multi-stage build
FROM openjdk:17 AS builder

WORKDIR /userAccountSystem

COPY ./mvnw .
COPY ./.mvn ./.mvn
COPY ./pom.xml .

# Copy the entire project (excluding files listed in .dockerignore)
COPY . .

RUN ["./mvnw", "clean", "package", "-DskipTests"]

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /userAccountSystem

RUN wget -q https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.23/mysql-connector-java-8.0.23.jar

COPY --from=builder /userAccountSystem/target/*.jar userAccountSystem.jar
COPY --from=builder /userAccountSystem/src/main/resources /userAccountSystem/src/main/resources

ENTRYPOINT ["java", "-jar", "userAccountSystem.jar"]

RUN rm -rf /userAccountSystem/mvnw /userAccountSystem/.mvn /userAccountSystem/pom.xml /userAccountSystem/target
