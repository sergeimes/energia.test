cd web
ng build --configuration=staging
cd ..
cp -R web/dist/ src/main/resources/static/
mvn clean install
docker build -t energia-test -f Dockerfile .
