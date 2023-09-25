
FROM openjdk:17-jdk-slim
COPY target/book-1.0.jar book.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/book.jar"]

