FROM eclipse-temurin:21.0.1_12-jre-alpine

WORKDIR /app

COPY app/target/descomplicando-app.jar /app/descomplicando-app.jar
COPY domain/target/descomplicando-domain.jar /app/descomplicando-domain.jar

ENTRYPOINT ["java","-jar","/app/descomplicando.jar"]
