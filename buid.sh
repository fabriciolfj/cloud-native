cd catalog-service
./gradlew clean build
docker build -t fabricio211/catalog-service:latest .
cd ..

cd order-service
./gradlew clean build
docker build -t fabricio211/order-service:latest .
cd ..

cd dispacher-service
./gradlew clean build
docker build -t fabricio211/dispacher-service:latest .
cd ..

eval $(minikube docker-env)
docker push fabricio211/catalog-service:latest
docker push fabricio211/order-service:latest
docker push fabricio211/dispacher-service:latest
