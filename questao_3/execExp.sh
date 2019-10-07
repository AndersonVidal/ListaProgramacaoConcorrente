echo "########## Init experiment ##########"

echo "======= Generating compiled files ======="
gcc generatorThreads.c -lpthread -o gt
echo "gt -> generatorThreads"
gcc generatorProcess.c -o gp
echo "gp -> generatorProcess"

echo "======= Time consume ======="

echo " *** Threads *** "

echo "# N = 100"
perf stat --repeat=30 -o threads_100.txt ./gt 100
echo""

echo "# N = 1000"
perf stat --repeat=30 -o threads_1k.txt ./gt 1000
echo""

echo "# N = 10000"
perf stat --repeat=30 -o threads_10k.txt ./gt 10000
echo""

echo""

echo " *** Process *** "
echo "# N = 100"
perf stat --repeat=30 -o proc_100.txt ./gp 100
echo""

echo "# N = 1000"
perf stat --repeat=30 -o proc_1k.txt ./gp 1000
echo""

echo "# N = 10000"
perf stat --repeat=10 -o proc_10k.txt ./gp 10000 ## Couldn't repeat more.
echo""


