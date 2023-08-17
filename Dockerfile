FROM openjdk:17-jdk-oracle
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=rest/target/tinqin-bff.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]