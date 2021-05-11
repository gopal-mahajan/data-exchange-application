FROM openjdk:8
EXPOSE 8080
ADD target/data-exchange-docker.jar data-exchange-docker.jar
ENTRYPOINT ["java","-jar","/data-exchange-docker.jar"]