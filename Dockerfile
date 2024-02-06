FROM openjdk:17
ADD target/grade-submission-0.0.1-SNAPSHOT.jar grade-submission-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "grade-submission-0.0.1-SNAPSHOT.jar"]