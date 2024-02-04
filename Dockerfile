FROM openjdk:18-jdk-alpine

EXPOSE 5500

ADD build/libs/moneytransferservice-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]