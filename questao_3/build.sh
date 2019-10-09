#!/bin/bash

echo "########## Init experiment ##########"

./script.sh &> log.txt
./scriptWithSleep.sh &> logWithSleep.txt

