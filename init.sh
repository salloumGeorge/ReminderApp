cd api

./gradlew clean build

cd ../scheduler

./gradlew clean build

cd ../workers

./gradlew clean build

cd ..

docker-compose up -d --build
```