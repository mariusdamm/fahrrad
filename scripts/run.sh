#!/bin/bash

read -p "Run detached? (y/n): " answer

if [[ "$answer" == "y" || "$answer" == "Y" ]]; then
    docker compose -f ../docker-compose.yml up --build -d

elif [[ "$answer" == "n" || "$answer" == "N" ]]; then
    docker compose -f ../docker-compose.yml up --build

else
    echo "Unknown answer. Please answer with 'y' or 'n'."
fi
