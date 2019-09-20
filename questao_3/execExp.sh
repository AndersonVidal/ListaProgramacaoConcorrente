echo "########## Init experiment ##########"

echo "======= Generating compiled files ======="
gcc generatorThreads.c -lpthread -o gt
echo "gt -> generatorThreads"
gcc generatorProcess.c -o gp
echo "gp -> generatorProcess"

echo "======= Time consume ======="

echo " *** Threads *** "

echo "# N = 100"
time ./gt 100
echo""

echo "# N = 1000"
time ./gt 1000
echo""

echo "# N = 10000"
time ./gt 10000
echo""

echo""

echo " *** Process *** "
echo "# N = 100"
time ./gp 100
echo""

echo "# N = 1000"
time ./gp 1000
echo""

echo "# N = 10000"
time ./gp 10000
echo""


