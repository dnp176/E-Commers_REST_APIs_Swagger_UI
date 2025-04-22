#!/bin/bash
# Ensure that the directory exists
mkdir -p /home/ubuntu/app

# Run the application in background
nohup java -jar /home/ubuntu/app/app.jar > /home/ubuntu/app.log 2>&1 &
