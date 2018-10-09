FROM openjdk:8-jre-alpine
VOLUME /tmp
ARG JAR_FILE=kubernates-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]


#FROM alpine:edge
#RUN apk add --no-cache openjdk8
#COPY files/UnlimitedJCEPolicyJDK8/* \
#  /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
