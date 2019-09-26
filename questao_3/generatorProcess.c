#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {
	int n = atoi(argv[1]);
	int signal;
	int cpid;
	for (int i = 0; i < n; i++) {
		int forkStatus = fork();
		if (forkStatus == 0) {
			exit(0);
		} else if (forkStatus == -1) {
			printf("Error creating process");
		}
	}
	do {
		cpid = wait(&signal);
	} while (cpid > 0);
}
