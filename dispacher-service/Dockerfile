FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=/build/libs/*.jar

COPY ${JAR_FILE} dispacher-service.jar


ENTRYPOINT ["java", "-jar", "dispacher-service.jar"]