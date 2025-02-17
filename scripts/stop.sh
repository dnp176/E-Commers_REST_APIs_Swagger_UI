#!/bin/bash
# Java process ko stop karo jo JAR file run kar raha hai
pkill -f 'java -jar' || echo "No application running"
