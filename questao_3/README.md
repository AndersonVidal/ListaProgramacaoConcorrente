# Comparação entre uso de threads e processos

## Metodologia 

### Tempo de criação: 

O objetivo da análise é ver qual o custo de memória e tempo de processamento no uso de threads vs processos. Para isso, foram criados dois programas similares, um que usa thread e um que usa processos, que fazem:

1. Recebem um inteiro como parâmetro, o número de threads/processos a serem criados.
2. Cria todas a threads/processos.
	- A função da thread/processo será esperar por 1 segundos e terminar sua execução.
3. Printa o número de threads criadas e o tempo total para sua criação. O tempo de execução das threads não influência no tempo de criação.
4. Espera que eles terminem sua execução.

### Uso de Memória

O sleep passado para as threads/processos serve para dar tempo de a criação das estruturas ser completa. Nessa meio tempo, em ~0.7 segundos, é executado um ps aux que captura as informações dos processos relacionados ao nosso problema. Foi usado o Resident Set Size(RSS) como a métrica para uso de memória, ele é retornado no ps aux e representa a quantidade de memória alocada para aquele processo na memória RAM, o dado coletado foi o total de RSS dos processos, incluindo o processo que cria as threads/processos.

### Esperado

É esperado que a criação de threads seja mais leve, já que apenas é necessário criar uma nova stack no espaço de endereçamento lógico do processo, em contra partida, para criar um novo processo é necessário copiar todo o processo pai. Como threads também utilizam de memória compartilhada, é esperado que seu uso de memória seja menor, ainda mais com todas executando a mesma função, já para processos, cada processo necessitaria das suas próprias páginas na memória para poder executar, mesmo elas sendo iguais às dos outros processos, aumentando muito o número de page-faults e por consequente, de acesso à memória.

### Ambiente:

Os testes foram executados em uma máquina rodando Ubuntu Mate 18.04, Intel(R) Core(TM) i7-7500U CPU @ 2.70GHz, 8GB RAM DDR4 2133 MHz

### Resultados: 