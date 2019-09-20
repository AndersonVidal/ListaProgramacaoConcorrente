public class TestQuest2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(10);
        TestRoutine testAwait = new TestRoutine(0, cdl);
        Thread t_await = new Thread(testAwait);
        t_await.start();
        for (int i = 0; i < 10; i++) {
            TestRoutine testCountDown = new TestRoutine(1, cdl);
            Thread t_countDown = new Thread(testCountDown);
            t_countDown.start();
            t_countDown.join();
        }
        t_await.join();
    }
}
