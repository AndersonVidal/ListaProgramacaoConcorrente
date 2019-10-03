package questao2;

public class CountDownRoutine implements Runnable {

	private CountDownLatch cdl;
	
	public CountDownRoutine(CountDownLatch cdl) {
		this.cdl = cdl;
	}
	
	@Override
	public void run() {
		System.out.printf("Started execution(%d)\n", Thread.currentThread().getId());
		//SLEEP FOR A WHILE
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.cdl.countDown();
		System.out.printf("Finished execution, Cdown done(%d)\n", Thread.currentThread().getId());
	}
}
