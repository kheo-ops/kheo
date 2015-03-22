# kheo  
[![License](http://img.shields.io/:license-mit-blue.svg)](http://doge.mit-license.org)  

====
## Overview
Kheo is an agentless application dedicated to servers life follow-up. It performs connections in background in order to generate events that represent changes on servers (differences since the last connection). 

Changes detections are provided by plugins, each one assuming the responsibility to detect changes and generating associated events. Some sample plugins available <a href="https://github.com/kheo-ops/kheo-plugins">kheo-plugins</a> manage things like network interfaces, installed services, running processes or os/kernel informations.

Kheo relies on SSH to communicate with the servers to manage so it does not need any specific configuration. As a constraint, it needs a key or a password to contact remote hosts.


### Components
Kheo is separated into two components :
- An API, that is available at <a href="https://github.com/kheo-ops/kheo-core">kheo-core</a>
- A webapp, that is available at <a href="https://github.com/kheo-ops/kheo-web">kheo-web</a>

### Running
Run application layers in docker containers:

The database must be run first.
```
docker run -d -p 27017:27017 --name kheo-db mongo:latest
```

The backend container linked to the `kheo-db` container
```
docker run -d -p 8080:8080 -p 8081:8081 --name kheo-api --link kheo-db:kheo-db kheo-api
```

The web frontend container linked to the `kheo-api` container
```
docker run -d -p 8000:80 -v ${PWD}/kheo-web/dist:/var/www/html --name kheo-web --link kheo-api:kheo-api dockerfile/nginx
```

To make it easier to use, there is a fig config file that let you start each layer inside a container:
```sudo fig up -d```

## Deployment
Some sample deployments with ansible and fig are available in the repository <a href="https://github.com/kheo-ops/kheo-deployments">kheo-deployments</a>

There are sample playbooks for those topologies:
- all-in-one: All Kheo components are deployed on one machine
- simple: Each component is deployed on a machine
- container: Each component is deployed in a docker container.

You can execute the playbook following these steps:
```
sudo ansible-galaxy install bennojoy.mongo_mongod
sudo ansible-galaxy install jdauphant.nginx
sudo ansible-galaxy install ANXS.oracle-jdk
ansible-playbook -i inventory kheo.yml --private-key=key
```

