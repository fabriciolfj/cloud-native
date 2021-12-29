./gradlew clean build
docker build -t fabricio211/order-service:3.0.1 .
docker push fabricio211/order-service:3.0.1