FROM amazoncorretto:22-headless
WORKDIR /app
COPY target/songtagebuch.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
