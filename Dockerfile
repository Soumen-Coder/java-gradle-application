FROM openjdk:17-oracle

EXPOSE 8080

COPY ./build/libs/java-gradle-application-1.0.0.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "java-gradle-application-1.0.0.jar"]