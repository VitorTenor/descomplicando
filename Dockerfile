FROM eclipse-temurin:21.0.1_12-jre-alpine

WORKDIR /app

# Copie o arquivo POM para o diretório de trabalho
COPY app/pom.xml .

# Copie os arquivos de código-fonte para o diretório de trabalho
COPY app/src ./src

# Instale o Maven
RUN apk add --no-cache maven

# Baixe as dependências do Maven e compile o código-fonte
RUN mvn clean install

# Copie os artefatos gerados para o diretório de trabalho
COPY app/target/app-0.0.1-SNAPSHOT.jar ./app.jar
COPY domain/target/domain-0.0.1-SNAPSHOT.jar ./domain.jar

# Comando para executar a aplicação Java após a construção com o Maven
CMD ["java", "-jar", "app.jar"]
