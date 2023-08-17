FROM openjdk:17
WORKDIR /BFF-ZooStore-ItemStorage
COPY rest/target/tinqin-bff.jar bff.jar
EXPOSE 8083
CMD ["java", "-jar", "bff.jar"]