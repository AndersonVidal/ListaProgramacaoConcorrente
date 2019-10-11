# Comparação entre uso de threads e processos

## Metodologia 

### Tempo de criação

O objetivo da análise é ver qual o custo de memória e tempo de processamento no uso de threads vs processos. Para isso, foram criados dois programas similares, um que usa thread e um que usa processos, que fazem:

1. Recebem um inteiro como parâmetro, o número de threads/processos a serem criados.
2. Cria todas a threads/processos.
	- A função da thread/processo será esperar por 1 segundos e terminar sua execução.
3. Printa o número de threads criadas e o tempo total para sua criação. O tempo de execução das threads não influência no tempo de criação.
4. Espera que eles terminem sua execução.

### Uso de Memória

O sleep passado para as threads/processos serve para dar tempo de a criação das estruturas ser completa. Nessa meio tempo, em ~0.7 segundos, é executado um ps aux que captura as informações dos processos relacionados ao nosso problema. Foi usado o Resident Set Size(RSS) como a métrica para uso de memória, ele é retornado no ps aux e representa a quantidade de memória alocada para aquele processo na memória RAM, o dado coletado foi o total de RSS dos processos, incluindo o processo que cria as threads/processos.

### Dados obtidos

O experimento foi realizado com diversos números de instâncias de threads e processos, para assim obtermos o impacto no tempo e na memória em situações diferentes. A quantidade de instâncias obedeceu a seguinte progressão: 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000. Para cada quantidade, formam feitas 100 execuções do programa descrito acima, obtendo assim 100 amostras de memória total e tempo total para cada uma das quantidades de instâncias.

Para garantir que seria possível experimentar com esse número de threads/processos, o experimento foi realizado algumas vezes, utilizando checagem de erros na função que cria threads e processos. Dessa forma, garantimos que os N processos conseguiam ser criados e os dados mensurados de forma correta.

Quanto à memória, assumimos que o ps, acordando dentro de 0.7 segundos após o inicio da execução de cada programa, consiga pegar o snapshot de todos os processos criados na memória. Alguns testes foram feitos com sucesso. 

### Resultados Esperados

É esperado que a criação de threads seja mais leve (consuma menos memória e aconteça em menos tempo), já que apenas é necessário criar uma nova stack no espaço de endereçamento lógico do processo, enquanto que para criar um novo processo é necessário realizar uma cópia de todo o processo pai. Como threads também utilizam de memória compartilhada, é esperado que seu uso de memória seja menor, ainda mais com todas executando a mesma função, já para processos, cada processo necessitaria das suas próprias páginas na memória para poder executar, mesmo que sejam iguais às dos outros processos, aumentando muito o número de page-faults e, consequentemente, de acesso à memória.

### Ambiente

O experimento e a coleta de dados foi realizada em uma máquina com as seguintes configurações:

- Sistema operacional: Ubuntu Mate 18.04
- Processador: Intel(R) Core(TM) i7-7500U @ 2.70GHz (2 núcleos e 4 threads)
- Memória RAM: 8GB DDR4 2133 MHz

A máquina não teve nenhuma interferência de usuário durante o experimento.

## Resultados

Para plotar os gráficos dos dados obtidos, foi utilizado o script Python (versão 3.7.3) presente neste repositório ([aqui](output/plot_graphics.py)) e foram utilizadas as bibliotecas Numpy e Matplotlib (o script pode ser facilmente executado utilizando o Anaconda, caso não se deseje instalar as bibliotecas separadamente.

![alt text](output/rect_proc_x_thread_time.png?raw=true)
![alt text](output/rect_proc_x_thread_mem.png?raw=true)

Como é possível observar com os gráficos acima, os resultados esperados de fato se concretizaram, onde a quantidade de memória utilizada pelos processos foi (muito) maior que a utilizada pelas threads, além do tempo de criação dos processos também ter sido maior do que o de criação das threads.

Para que seja possível ter uma melhor dimensão da diferença dos valores, temos abaixo uma visualização gráfica do crescimento dos valores, tanto de memória quanto de tempo, comparando threads com processos. A partir desses dados, pudemos constatar que , em nosso experimento, os processos apresentaram em média um consumo de memória 556,41% maior que o de threads e um tempo 170,69% maior para criar todas as instâncias.

![alt text](output/bar_increase_perc.png?raw=true)

Além disso, verificamos o comportamento de cada thread e processo individualmente, ou seja, com o aumento da quantidade de instâncias, como fica a divisão de tempo/instância e memória/instância. Essa informação esta disposta nos dois gráficos seguintes.

![alt text](output/bar_mean_mem_unit.png?raw=true)

Observamos acima que houve uma diminuição na quantidade de memória utilizada por uma única thread ou processo no decorrer do incremento do número de instâncias. Acreditamos que isso ocorreu porque foi contabilizado o tamanho do processo que originou os demais processos e as threads. Esse tamanho, para uma quantidade menor de instâncias, acaba influenciando bastante no todo, entretanto, para uma grande quantidade de instâncias, acabamos observando uma maior estabilidade, já que esse valor acaba por não ser mais tão significativo em relação ao total.

![alt text](output/bar_mean_time_unit.png?raw=true)

Já se tratando de tempo, observamos que também é retratada uma diminuição no tempo necessário para inicializar uma nova thread ou processo no decorrer do incremento do número de instâncias.

Esse fenomeno pode ser devido a interferências relacionadas a escalonamento, operações do SO ou outros programas, já que qualquer variação pequena pode ser significativa em relação ao tamanho do experimento. Porém, para muitas instâncias, os dados se tornam bem mais consistentes, provavelmente devido à diluição das interferências em relação ao total.

### Boxplots

Os dados obtidos com o experimento foram organizados em gráficos de Boxplot, para cada número de instâncias e para dados de memória e tempo. Os gráficos para processos e threads foram colocados lado a lado, entretanto, deve-se observar que a escala, na maioria dos casos, não estará equivalente, pois fazê-lo iria dificultar a visualização (principalmente para os números maiores de instâncias, dado que a discrepância entre os valores tende a ser maior).

O principal intuito com as representações abaixo é o de observar a forma como se deu a distribuição dos dados por nós obtidos e, especialmente, verificar a quantidade e a disposição de valores discrepantes na distribuição.

É possível observar uma grande dispersão e presença considerável de valores discrepantes principalmente para os dados de processos e relativos à valores de memória (gráficos logo abaixo). 

### Tempo

#### 10 à 50 Instâncias
![alt text](output/boxplot_10_50_time.png?raw=true)

#### 60 à 100 Instâncias
![alt text](output/boxplot_60_100_time.png?raw=true)

#### 1K à 5K Instâncias
![alt text](output/boxplot_1K_5K_time.png?raw=true)

#### 6K à 10K Instâncias
![alt text](output/boxplot_6K_10K_time.png?raw=true)


### Memória

#### 10 à 50 Instâncias
![alt text](output/boxplot_10_50_mem.png?raw=true)

#### 60 à 100 Instâncias
![alt text](output/boxplot_60_100_mem.png?raw=true)

#### 1K Instâncias
![alt text](output/boxplot_1K_mem.png?raw=true)

#### 2K Instâncias
![alt text](output/boxplot_2K_mem.png?raw=true)

#### 3K Instâncias
![alt text](output/boxplot_3K_mem.png?raw=true)

#### 4K Instâncias
![alt text](output/boxplot_4K_mem.png?raw=true)

#### 5K Instâncias
![alt text](output/boxplot_5K_mem.png?raw=true)

#### 6K Instâncias
![alt text](output/boxplot_6K_mem.png?raw=true)

#### 7K Instâncias
![alt text](output/boxplot_7K_mem.png?raw=true)

#### 8K Instâncias
![alt text](output/boxplot_8K_mem.png?raw=true)

#### 9K Instâncias
![alt text](output/boxplot_9K_mem.png?raw=true)

#### 10K Instâncias
![alt text](output/boxplot_10K_mem.png?raw=true)
