# Install dependencies
mongod 2>&1 >/dev/null &
cd kheo-api && mvn clean package -DskipTests && cd ..
docker pull stephens/sshd
gem install cucumber
gem install httparty

# Run servers
chmod a+x kheo-test/wait_for_server.sh
java -jar kheo-api/target/kheo-api.jar server kheo-api/config/kheo-api-ci.yml 2>&1 >kheo.log  &
sshd_container_id=$(docker run -d -p 22222:22 stephens/sshd)

# Test suite
bash kheo-test/wait_for_server.sh 
cd kheo-test && cucumber

# Cleanup
kill $(ps aux | grep -v "grep" | grep "java -jar kheo-api/target/kheo-api.jar server kheo-api/config/kheo-api-ci.yml" | awk '{print $2}')
cd kheo-api && mvn clean
docker stop $sshd_container_id
mongo kheo --eval "db.dropDatabase()"
mongo admin --eval "db.shutdownServer()"