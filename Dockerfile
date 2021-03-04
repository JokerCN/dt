FROM harbor.cicconline.com/paas/openjdk:8u252-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY ./target/nacos-test-0.0.1-SNAPSHOT.jar .
RUN \
    mv nacos-test-0.0.1-SNAPSHOT.jar nacos-test.jar

CMD ["java", "-jar", "nacos-test.jar"]