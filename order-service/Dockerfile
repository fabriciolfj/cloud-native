FROM adoptopenjdk:11-jre-hotspot

ADD build/libs/*.jar /app/order-service.jar

ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]