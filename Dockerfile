FROM java:8-jdk-alpine
ENV LANG C.UTF-8
ENV JVM_OPTS -server -Xmx512M -Xms512M -XX:MetaspaceSize=64m -verbose:gc -verbose:sizes -XX:+UseG1GC -XX:MaxGCPauseMillis=50
RUN apk add --no-cache tzdata
ENV TZ="Asia/Shanghai"
ADD target/*.jar app.jar
ENTRYPOINT  java ${JVM_OPTS} -Djava.security.egd=file:/dev/./urandom  -Dfile.encoding=UTF-8 -jar app.jar