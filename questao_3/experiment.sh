echo "======= Generating compiled files without sleep command ======="
#gcc generatorThreads.c -lpthread -o gt
echo "gt -> generatorThreads"
gcc generatorProcessCreate.c -o gpc.out
echo "gp -> generatorProcess"
gcc generatorThreadCreate.c -lpthread -o gtc.out

echo "Repetition rate: ${REPETITION_RATE:= 100}"

echo "======= Colleting data - small number of threads ======= (ETA: 2000 SECONDS)"

echo "NumberOfThreads/Process: ${NUMBER_OF_THREADS:= 10, 20, 30, 40, 50, 60, 70, 80, 90, 100}"

mkdir -p output/
echo "NumberCreated, TotalCreationTime, TotalRSS" > output/procCreationTimeSmall.csv
echo "NumberCreated, TotalCreationTime, TotalRSS" > output/threadCreationTimeSmall.csv

for n in ${NUMBER_OF_THREADS};
do
    echo "Running Experiment for ${n} threads/processes - small numbers."
    for i in `seq 0 $REPETITION_RATE`
    do
        #####  EXPERIMENT FOR THREADS - SMALL #####
        ./gtc.out ${n} 1 >> output/threadCreationTimeSmall.csv &
        BGPID=$! #GETS PID OF GTC, NEED TO WAIT
        GPC_MEMORY=$(sleep 0.6 && ps aux | grep "./[g]tc.out" | awk -F ' ' '{sum += $6} END{print sum}') #CAPTURE MEMORY WITH PS AUX
        wait ${BGPID} &&  echo "${GPC_MEMORY}" >> output/threadCreationTimeSmall.csv #KILL PROCESS AND APPEND MEMORY

        #####  EXPERIMENT FOR PROCESS - SMALL #####
        ./gpc.out ${n} 1 >> output/procCreationTimeSmall.csv &  ##EXECUTE EXPERIMENT AND SAVE RESULTS TO FILE
        BGPID=$! #GETS PID OF GPC, NEED TO WAIT
        GPC_MEMORY=$(sleep 0.6 && ps aux | grep "./[g]pc.out" | awk -F ' ' '{sum += $6} END{print sum}') #CAPTURE MEMORY WITH PS AUX
        wait ${BGPID} &&  echo "${GPC_MEMORY}" >> output/procCreationTimeSmall.csv #KILL PROCESS AND APPEND MEMORY
    done 
done

echo "======= Colleting data - big number of threads ======= (ETA: 2000 SECONDS)"

echo "NumberOfThreads/Process: ${NUMBER_OF_THREADS:= 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000}"

mkdir -p output/
echo "NumberOfProcess, TotalCreationTime, TotalRSS" > output/procCreationTime.csv
echo "NumberOfThreads, TotalCreationTime, TotalRSS" > output/threadCreationTime.csv

for n in ${NUMBER_OF_THREADS};
do
    echo "Running Experiment for ${n} threads/processes."
    for i in `seq 0 $REPETITION_RATE`
    do
        #####  EXPERIMENT FOR THREADS - BIG #####
        ./gtc.out ${n} 1 >> output/threadCreationTime.csv &
        BGPID=$! #GETS PID OF GTC, NEED TO WAIT
        GPC_MEMORY=$(sleep 0.7 && ps aux | grep "./[g]tc.out" | awk -F ' ' '{sum += $6} END{print sum}') #CAPTURE MEMORY WITH PS AUX
        wait ${BGPID} &&  echo "${GPC_MEMORY}" >> output/threadCreationTime.csv #KILL PROCESS AND APPEND MEMORY

        #####  EXPERIMENT FOR PROCESS - BIG #####
        ./gpc.out ${n} 1 >> output/procCreationTime.csv &  ##EXECUTE EXPERIMENT AND SAVE RESULTS TO FILE
        BGPID=$! #GETS PID OF GPC, NEED TO WAIT
        GPC_MEMORY=$(sleep 0.7 && ps aux | grep "./[g]pc.out" | awk -F ' ' '{sum += $6} END{print sum}') #CAPTURE MEMORY WITH PS AUX
        wait ${BGPID} &&  echo "${GPC_MEMORY}" >> output/procCreationTime.csv #KILL PROCESS AND APPEND MEMORY
    done 
done

echo "======= FINISHED ======="

rm *.out