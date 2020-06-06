##  Note: This Docker file is not always used for docker builds. Instead we have embraced jib
##  https://github.com/GoogleContainerTools/jib . to build and push an image simply execute:
##  ./gradlew jib --image=sameuelsegal/gigety-web-api
From openjdk:8-jdk-alpine
copy ./build/libs/gigety-web-api-0.0.1-SNAPSHOT.jar gigety-web-api-0.0.1-SNAPSHOT.jar
#EXPOSE 8000
#ENTRYPOINT ["sh","-c","java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar gigety-web-api-0.0.1-SNAPSHOT.jar"]
CMD ["java","-jar","gigety-web-api-0.0.1-SNAPSHOT.jar"]
