FROM dockerfile/java:openjdk-7-jdk
ADD target/kheo-api.jar /data/kheo-api.jar
ADD config/kheo-api-dev.yml /data/kheo-api.yml
WORKDIR /data
CMD java -jar kheo-api.jar server
EXPOSE 8080