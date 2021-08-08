FROM openjdk:8-jdk-alpine

MAINTAINER nandathoomathy@gmail.com

ADD build/libs/order-processor-api-0.0.1-SNAPSHOT.jar /

EXPOSE 8082

ENTRYPOINT ["java","-jar", "order-processor-api-0.0.1-SNAPSHOT.jar"]
