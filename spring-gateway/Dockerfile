FROM adoptopenjdk:16-jdk

ARG JAR_FILE=/build/libs/*.jar

COPY ${JAR_FILE} edge-service.jar

ENTRYPOINT ["java", "-jar", "edge-service.jar"]