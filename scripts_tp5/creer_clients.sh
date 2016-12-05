#!/usr/bin/env bash

for i in {1..8}
do 
  curl -X POST localhost:5000/supermarche/clients
done
