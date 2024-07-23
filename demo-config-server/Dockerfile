#Stage 1: Build the application
FROM openjdk:22-jdk-slim AS build

#Install dependencies for building the application
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

#Set the working dir \
WORKDIR /app

#Copy the maven config and source code
COPY pom.xml .
COPY src ./src

#Package the application : clean the target directory and packages ot into jar
RUN mvn clean package

#Stage 2 Create final image
FROM openjdk:22-jdk-slim

#Set the working dir \
WORKDIR /app

#Copy the jar file from the build stage and rename to app.jar
COPY --from=build /app/target/config-server-1.0-SNAPSHOT.jar app.jar

#Expose port
EXPOSE 8888

#Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]

