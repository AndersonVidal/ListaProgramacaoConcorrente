package questao2;

public class CountDownLatchTest {

	public static void main(String[] args) throws InterruptedException {
		int numThreads = 10;
		CountDownLatch cdl = new CountDownLatch(numThreads);
		CountDownRoutine routine = new CountDownRoutine(cdl);
		
		System.out.println("Começando...");
		for (int i = 0; i < numThreads; i++) {
			new Thread(routine).start();
		}
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		} // espera 4 countDowns no latch

		Thread.sleep(1000); //Sleeping so it will print after threads.
		System.out.printf(
				"Essa mensagem só vai ser impressa quando todas as %d threads terminarem de executar e chamarem o método countDown no Latch.\n", numThreads);
		}
}
