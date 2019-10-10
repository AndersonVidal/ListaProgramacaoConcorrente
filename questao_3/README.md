# Comparação entre uso de threads e processos

## Metodologia 

### Tempo de criação: 

O objetivo da análise é ver qual o custo de memória e tempo de processamento no uso de threads vs processos. Para isso, foram criados dois programas similares, um que usa thread e um que usa processos, que fazem:

1.  Recebem um inteiro como parâmetro, o número de threads/processos a serem criados.
2. Cria todas a threads/processos.
	- A função da thread/processo será esperar por 0.2 segundos e terminar sua execução.
3. Printa o número de threads criadas e o tempo total para sua criação. O tempo de execução das threads não influência no tempo de criação.
4. Espera que eles terminem sua execução.

O tempo de criação e o número de threads são adicionados à um arquivo .csv.