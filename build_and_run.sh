#!/bin/bash

echo "Building the project with Gradle..."
./gradlew bootJar

if [ $? -eq 0 ]; then
    echo "Build successful. Running the application..."
    java -jar build/libs/prop12-1-0.0.1-SNAPSHOT.jar
else
    echo "Build failed. Please check the logs for details."
    exit 1
fi
