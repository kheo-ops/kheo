#!/bin/bash
echo "test api"

# Tries a command 10 times sleeping 3 seconds between tries
# $1 : command
# $2 : expected exit code
function retry {  
  try_sleep=3
  retry="0"
  res="-1"
  while [ $res -ne $2 ] && [ $retry -lt 10 ]
  do
    echo "$1"
    res=$(echo "$1" | /bin/bash)    
    retry=$[$retry+1]
    echo "expected: $2 - actual: $res"    
    if [ $res -eq  $2 ]; then 
      break
    fi
    echo "try : $retry - $1"
    sleep $try_sleep
  done
}

function wait_for_server {
  retry 'curl -o /dev/null -w "%{http_code}" http://localhost:8081/healthcheck' 200
}

wait_for_server
