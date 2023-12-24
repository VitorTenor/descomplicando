FROM eclipse-temurin:21.0.1_12-jre-alpine

WORKDIR /app

COPY app/pom.xml .

COPY app/src ./src

COPY domain ./domain

RUN apk add --no-cache maven

RUN mvn clean install -DskipTests

COPY app/target/app-0.0.1-SNAPSHOT.jar ./app.jar
COPY domain/target/domain-0.0.1-SNAPSHOT.jar ./domain.jar

CMD ["java", "-jar", "app.jar"]
