echo "======= Generating compiled files without sleep command ======="
gcc generatorThreads.c -lpthread -o gt
echo "gt -> generatorThreads"
gcc generatorProcess.c -o gp
echo "gp -> generatorProcess"

echo "======= Colleting data ======="

echo " *** Threads *** "

echo "# N = 100"
echo "(1)"
time ./gt 100
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 100
echo""
echo "(2)"
time ./gt 100
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 100
echo""
echo "(3)"
time ./gt 100
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 100
echo""
echo "(4)"
time ./gt 100
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 100
echo"" 

echo "# N = 1000"
echo "(1)"
time ./gt 1000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 1000
echo""
echo "(2)"
time ./gt 1000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 1000
echo""
echo "(3)"
time ./gt 1000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 1000
echo""
echo "(4)"
time ./gt 1000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 1000
echo"" 

echo "# N = 10000"
echo "(1)"
time ./gt 10000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 10000
echo""
echo "(2)"
time ./gt 10000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 10000
echo""
echo "(3)"
time ./gt 10000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 10000
echo""
echo "(4)"
time ./gt 10000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gt 10000
echo"" 

echo""

echo " *** Process *** "
echo "# N = 100"
echo "(1)"
time ./gp 100
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 100
echo""
echo "(2)"
time ./gp 100
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 100
echo""
echo "(3)"
time ./gp 100
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 100
echo""
echo "(4)"
time ./gp 100
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 100
echo""

echo "# N = 1000"
echo "(1)"
time ./gp 1000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 1000
echo""
echo "(2)"
time ./gp 1000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 1000
echo""
echo "(3)"
time ./gp 1000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 1000
echo""
echo "(4)"
time ./gp 1000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 1000
echo""

echo "# N = 10000"
echo "(1)"
time ./gp 10000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 10000
echo""
echo "(2)"
time ./gp 10000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 10000
echo""
echo "(3)"
time ./gp 10000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 10000
echo""
echo "(4)"
time ./gp 10000
/usr/bin/time -v /home/moi3/Documents/ListaProgramacaoConcorrente/questao_3/gp 10000
echo""


