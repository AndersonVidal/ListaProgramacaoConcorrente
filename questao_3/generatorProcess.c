#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {
	int n = atoi(argv[1]);
	int signal;
	for (int i = 0; i < n; i++) {
		if (fork() == 0) {
			exit(0);
		} else {
			wait(&signal);	
		}
	}
}
