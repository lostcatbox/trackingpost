FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar  #jar파일이 있는 경로
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"] #jar 파일 실행