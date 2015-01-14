# kheo
====

## Overview
Kheo is an agentless application dedicated to servers management, including softwares inventory, os and network informations.

Kheo relies on SSH to communicate with the servers to manage so it does not need any specific configuration. As a constraint, it needs a key to contact remote hosts.

To register servers, you have many solutions:
- Using an inventory file just like Ansible does.
- Using the API
- Using the webapp

Once your servers have been registered, you can obtain informations like:
- Network interfaces and IPs
- OS type and version
- Users
- Resources (ram, disk, cpu, ...)
- Running processes
- Installed packages

## Underlying technologies
Kheo is build with the following technologies:
- MongoDB
- AngularJS
- Dropwizard   

## Running
Run application layers with docker containers
```
sudo docker build -t kheo-api . && sudo docker run -d -p 8080:8080 -p 8081:8081 kheo-api
sudo docker build -t kheo-web . && sudo docker run -d -p 80:80 kheo-web
sudo docker run -d -p 27017:27017 mongo:latest
```

To make it easier to use, there is a fig config file that let you start each layer inside a container:
`sudo fig up -d`

## Deployment
Kheo comes with an Ansible playbook that deploys components through your machines. 

You can execute the playbook following these steps:
- sudo ansible-galaxy install lesmyrmidons.mongodb
- sudo ansible-galaxy install bennojoy.nginx
- sudo ansible-galaxy install smola.java
- ansible-playbook -i inventory kheo.yml

Inventory file must define the following groups:
- kheo-db
- kheo-api
- kheo-web
