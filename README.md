# kheo
====

## Statistics
Functional tests: [![Circle CI](https://circleci.com/gh/migibert/kheo/tree/master.svg?style=svg)](https://circleci.com/gh/migibert/kheo/tree/master)

[![Issue Stats](http://issuestats.com/github/migibert/kheo/badge/pr)](http://issuestats.com/github/migibert/kheo)
[![Issue Stats](http://issuestats.com/github/migibert/kheo/badge/issue)](http://issuestats.com/github/migibert/kheo)

## Overview
Kheo is an agentless application dedicated to servers management, including softwares inventory, os and network informations. It performs connections in background in order to generate events that represent changes on servers (differences since the last connection).

In addition, it will discover routes between servers and display them as a graph in the web ui.

Kheo relies on SSH to communicate with the servers to manage so it does not need any specific configuration. As a constraint, it needs a key to contact remote hosts.

To register servers, you have many solutions:
- Using an inventory file just like Ansible does.
- Using the API
- Using the webapp

Once your servers have been registered, you can obtain informations like:
- Network interfaces, IPs and routes
- OS type and version
- Users
- Resources (ram, disk, cpu, ...)
- Running processes
- Installed packages
 
Moreover, Kheo discovers your servers configuration at regular intervals and stores delta between configuration as events. You can select events you want to store and those that do not have interest for you.

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
```sudo fig up -d```

## Deployment
Kheo comes with sample Ansible playbooks that deploys components through your machines.

There are sample playbooks for those topologies:
- all-in-one: All Kheo components are deployed on one machine
- simple: Each component is deployed on a machine

You can execute the playbook following these steps:
```
sudo ansible-galaxy install bennojoy.mongo_mongod
sudo ansible-galaxy install jdauphant.nginx
sudo ansible-galaxy install ANXS.oracle-jdk
ansible-playbook -i inventory kheo.yml --private-key=key
```

## Testing
Cucumber is used to validate API behavior. CircleCI runs Cucumber tests at each build. 

To run it in local, use the script `local.sh`
