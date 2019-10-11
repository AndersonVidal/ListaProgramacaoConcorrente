#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>

void* routine (void *sleepTime) {
	sleep(*((double *) sleepTime));
	pthread_exit(0);
}

int main(int argc, char *argv[]) {
	int n = atoi(argv[1]); // numero de threads
	double *sleepTime = malloc(sizeof(*sleepTime));
	*sleepTime = atof(argv[2]); 

	pthread_t threads[n];

	clock_t begin = clock();
	for (int i = 0; i < n; i++) {
		int err = pthread_create(&threads[i], NULL, routine, (void *) sleepTime);
		/*if (err) { // Used to test pthread_create error.
			printf("ERROR in pthread create (%d). ErrorCode: %d\n", i, err);
			exit(1);		
		}*/
	}
	clock_t end = clock();

	double time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
	printf("%d, %f, ", n, time_spent);

	for (int i = 0; i < n; i++) {
		int err = pthread_join(threads[i], NULL);
		if (err) {
			printf("ERROR in pthread join. ErrorCode: %d\n", err);		
		}
	}
}


