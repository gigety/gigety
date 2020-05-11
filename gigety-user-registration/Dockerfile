##  Note: This Docker file is not always used for docker builds. Instead we have embraced jib
##  https://github.com/GoogleContainerTools/jib . to build and push an image simply execute:
##  ./gradlew jib --image=sameuelsegal/gigety-user-registration
From openjdk:8
copy ./build/libs/gigety-user-registration-0.0.1-SNAPSHOT.jar gigety-user-registration-0.0.1-SNAPSHOT.jar
EXPOSE 8084
CMD ["java","-jar","gigety-user-registration-0.0.1-SNAPSHOT.jar"]
