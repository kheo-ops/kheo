kheo
====

Run application inside containers: 
sudo docker build -t kheo-api . && sudo docker run -d -p 8080:8080 -p 8081:8081 kheo-api
sudo docker build -t kheo-web . && sudo docker run -d -p 80:80 kheo-web
sudo docker run -d -p 27017:27017 mongo:latest

To make it easier to use, there is a fig config file that let you start each layer inside a container:
sudo fig up -d
