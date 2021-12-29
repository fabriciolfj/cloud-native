./gradlew clean build
docker build -t fabricio211/catalog-service:2.0.4 .
docker push fabricio211/catalog-service:2.0.4