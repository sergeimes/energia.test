FROM openjdk:14-alpine

COPY target/cafeteria-0.0.1-SNAPSHOT.jar /app/

CMD ["sh", "-c", "java -jar /app/cafeteria-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
