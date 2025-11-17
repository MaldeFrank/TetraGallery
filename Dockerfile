FROM maven:3.9.4-eclipse-temurin-21

WORKDIR /app

COPY . /app

RUN mvn clean package

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "target/TetraGallery-0.0.1-SNAPSHOT.jar"]
