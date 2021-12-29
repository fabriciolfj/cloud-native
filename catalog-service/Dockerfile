FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=/build/libs/catalog-service-2.0.4.jar

COPY ${JAR_FILE} catalog-service.jar

ENTRYPOINT ["java", "-jar", "catalog-service.jar"]