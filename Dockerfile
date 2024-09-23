# Use a multi-stage build
FROM openjdk:17 AS builder


WORKDIR /ekyc

# Copy only the necessary files for building
COPY ./mvnw .
COPY ./.mvn ./.mvn
COPY ./pom.xml .

# Copy the entire project (excluding files listed in .dockerignore)
COPY . .

# Build the application
RUN ["./mvnw", "clean", "package"]

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /ekyc

# Copy the JAR file to the /maven17 directory
COPY --from=builder /ekyc/target/*.jar ekyc.jar
COPY --from=builder /ekyc/.files .files

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "ekyc.jar"]

# Clean up unnecessary files after copying JAR
RUN rm -rf /app/mvnw /app/.mvn /app/pom.xml /app/target


