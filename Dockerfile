FROM openjdk:17
WORKDIR /app
EXPOSE 8082
COPY target/deliverySystem-0.0.1-SNAPSHOT.jar /app/deliverySystem.jar
ENTRYPOINT ["java", "-jar", "deliverySystem.jar"]
