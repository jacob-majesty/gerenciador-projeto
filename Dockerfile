# Usa a imagem oficial do OpenJDK 17 como base para a aplicação
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR da aplicação para o diretório de trabalho no container
COPY target/gerenciador-projeto.jar /app/gerenciador-projeto.jar

# Expõe a porta 8080 para acessar a aplicação do lado de fora do container
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/gerenciador-projeto.jar"]
