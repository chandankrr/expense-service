FROM amazoncorretto:21
MAINTAINER chandankrr
WORKDIR /app
COPY target/expense-service-0.0.1-SNAPSHOT.jar /app/expense-service-0.0.1-SNAPSHOT.jar
EXPOSE 9840
ENTRYPOINT ["java", "-jar", "/app/expense-service-0.0.1-SNAPSHOT.jar"]