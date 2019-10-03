package questao2;

public class CountDownLatch {

	 private Integer count; 
	
	 public CountDownLatch(int count) {
		 this.count = count;
	 }
	 
	 public synchronized void countDown() {
		 if (this.count > 0) {
			 this.count--;
			 if (this.count == 0) {
			 	this.notifyAll();
			 }
		 }
	 }
	 
	 public synchronized void await() throws InterruptedException {
		while (this.count > 0) {
			this.wait();
		}
	}

}
