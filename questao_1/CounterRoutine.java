public class CounterRoutine implements Runnable {
    private int count;
    private int start;
    private int end;
    private FairLock lockVar;

    public CounterRoutine(int start, int end) {
        this.count = 0;
        this.start = start;
        this.end = end;
        this.lockVar = new FairLock();
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
    	lockVar.lock();
        for (long i = this.start; i < this.end; i++) {
        	this.count++;
        }
    	lockVar.unlock();
    }
}
