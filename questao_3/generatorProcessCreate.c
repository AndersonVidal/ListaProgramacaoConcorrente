#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>


int main(int argc, char *argv[]) {
	int n = atoi(argv[1]);
	int sleepTime = atof(argv[2]);

	int signal;
	int cpid;

	clock_t begin = clock();
	for (int i = 0; i < n; i++) {
		int forkStatus = fork();
		if (forkStatus == 0) {
			sleep(sleepTime);
			exit(0);
		} else if (forkStatus == -1) {
			printf("Error creating process (%d)", i);
			exit(1);
		}
	}
	clock_t end = clock();

	double time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
	printf("%d, %f, ", n, time_spent);

	do {
		cpid = wait(&signal);
	} while (cpid > 0);
	exit(0);
}
