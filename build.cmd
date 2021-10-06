cd web
ng build --configuration=staging
cd ..
xcopy web\dist src\main\resources\static\ /e /y
mvn clean install
docker build -t energia-test -f Dockerfile .
