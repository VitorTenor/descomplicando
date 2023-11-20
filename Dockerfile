FROM openjdk:17-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

COPY app/target/app-0.0.1-SNAPSHOT.jar ./app.jar
COPY domain/target/domain-0.0.1-SNAPSHOT.jar ./domain.jar

CMD ["java", "-jar", "app.jar"]
