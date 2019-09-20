public class CountDownLatch {
    private int latch;

    public CountDownLatch(int latch) {
        if (latch < 0) {
            throw new IllegalArgumentException();
        }
        this.latch = latch;
    }

    public int getLatch() {
        return this.latch;
    }

    public void await() throws InterruptedException {
        while (this.latch > 0) {
            synchronized (this) {
                this.wait();
            }
        }
    }

    public synchronized void countDown() {
        if (this.latch > 0) {
            this.latch--;
            if (this.latch == 0) notifyAll();
        }
    }
}
