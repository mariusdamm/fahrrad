#!/bin/bash

docker compose -f ../server/docker-compose.test.yml up -d
docker logs -f fahrrad-app-test
docker wait fahrrad-app-test
docker compose -f ../server/docker-compose.test.yml down
