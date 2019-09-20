#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

void* routine (void *args) {
	pthread_exit(0);
}

int main(int argc, char *argv[]) {
	int n = atoi(argv[1]); // numero de threads
	pthread_t threads[n];
	for (int i = 0; i < n; i++) {
		int err = pthread_create(&threads[i], NULL, routine, NULL);
		if (err) {
			printf("ERROR in pthread create. ErrorCode: %d\n", err);		
		}
	}
	for (int i = 0; i < n; i++) {
		int err = pthread_join(threads[i], NULL);
		if (err) {
			printf("ERROR in pthread join. ErrorCode: %d\n", err);		
		}
	}
}


