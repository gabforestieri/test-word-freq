FROM gradle:6.8.3-jdk11 as build
COPY --chown=gradle:gradle . /src/
WORKDIR /src/
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim

RUN mkdir /app

COPY --from=build /src/build/libs/*.jar /app/w-frequency.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/w-frequency.jar"]
