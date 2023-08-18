FROM openjdk:17

RUN mkdir /bff-zoostore-itemstorage

WORKDIR /bff-zoostore-itemstorage

COPY rest/target/tinqin-bff.jar bff.jar

EXPOSE 8083

CMD ["java", "-jar", "bff.jar"]