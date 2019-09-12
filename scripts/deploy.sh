#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp target/IronMadness-1.0-SNAPSHOT.jar \
       root@51.15.56.37:/home/iron/

echo 'Restart server.....'

ssh root@51.15.56.37 << EOF

pgrep java | xargs kill -9
nohup java -jar IronMadness-1.0-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'
