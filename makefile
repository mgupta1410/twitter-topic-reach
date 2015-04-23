#!/bin/bash

java -jar ADM2.jar
python classifier.py $1
python cascade.py $2

