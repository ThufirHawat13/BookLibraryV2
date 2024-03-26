FROM bellsoft/liberica-openjdk-alpine:17.0.6
WORKDIR /app
COPY target/BookLibraryV2-0.0.1-SNAPSHOT.jar app/BookLibraryV2.jar
CMD ["java", "-jar", "app/BookLibraryV2.jar"]