FROM openjdk:17
ADD target/CBS-API-0.0.1-SNAPSHOT.jar LCBS.jar 
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "LCBS.jar"]