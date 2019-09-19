import java.util.concurrent.locks.LockSupport;

public class CounterRoutine implements Runnable, Lock {
    private int count;
    private int start;
    private int end;
    private LockVar lockVar;

    public CounterRoutine(int start, int end) {
        this.count = 0;
        this.start = start;
        this.end = end;
        this.lockVar = new LockVar();
    }

    public int getCount() {
        return count;
    }

    @Override
    public void lock(LockVar lockVar) {
        while (lockVar.guard.compareAndSet(1, 1));
        if (lockVar.flag.compareAndSet(1, 1)) {
            Thread thread = Thread.currentThread();
            lockVar.pushThread(thread);
            LockSupport.park();
        }
        lockVar.guard.set(0);
    }

    @Override
    public void unlock(LockVar lockVar) {
        while (lockVar.guard.compareAndSet(1, 1));
        if (lockVar.isEmpty()) {
            lockVar.flag.set(0);
        } else {
            Thread next = lockVar.getThread();
            LockSupport.unpark(next);
        }
        lockVar.guard.set(0);
    }

    @Override
    public void run() {
        this.lock(lockVar);
        for (long i = this.start; i < this.end; i++)
            this.count++;
        this.unlock(lockVar);
    }
}
