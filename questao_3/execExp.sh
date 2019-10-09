echo "########## Init experiment ##########"

echo "======= Generating compiled files ======="
gcc generatorThreads.c -lpthread -o gt
echo "gt -> generatorThreads"
gcc generatorProcess.c -o gp
echo "gp -> generatorProcess"

echo "======= Time consume ======="

echo " *** Threads *** "

echo "# N = 100"
echo "(1)"
time ./gt 100
echo""
echo "(2)"
time ./gt 100
echo""
echo "(3)"
time ./gt 100
echo""
echo "(4)"
time ./gt 100
echo"" 

echo "# N = 1000"
echo "(1)"
time ./gt 1000
echo""
echo "(2)"
time ./gt 1000
echo""
echo "(3)"
time ./gt 1000
echo""
echo "(4)"
time ./gt 1000
echo""

echo "# N = 10000"
echo "(1)"
time ./gt 10000
echo""
echo "(2)"
time ./gt 10000
echo""
echo "(3)"
time ./gt 10000
echo""
echo "(4)"
time ./gt 10000
echo""

echo""

echo " *** Process *** "
echo "# N = 100"
echo "(1)"
time ./gp 100
echo""
echo "(2)"
time ./gp 100
echo""
echo "(3)"
time ./gp 100
echo""
echo "(4)"
time ./gp 100
echo""

echo "# N = 1000"
echo "(1)"
time ./gp 1000
echo""
echo "(2)"
time ./gp 1000
echo""
echo "(3)"
time ./gp 1000
echo""
echo "(4)"
time ./gp 1000
echo""

echo "# N = 10000"
echo "(1)"
time ./gp 10000
echo""
echo "(2)"
time ./gp 10000
echo""
echo "(3)"
time ./gp 10000
echo""
echo "(4)"
time ./gp 10000
echo""


