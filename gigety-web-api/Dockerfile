From openjdk:8
copy ./build/libs/gigety-web-api-0.0.1-SNAPSHOT.jar gigety-web-api-0.0.1-SNAPSHOT.jar
EXPOSE 8000
ENTRYPOINT ["sh","-c","java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar gigety-web-api-0.0.1-SNAPSHOT.jar"]
