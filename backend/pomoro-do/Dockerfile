FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar","/app.jar","-Duser.timezone=Asia/Seoul"]
# -Duser.timezone=Asia/Seoul 옵션: 기본 타임존을 Asia/Seoul로 강제 설정