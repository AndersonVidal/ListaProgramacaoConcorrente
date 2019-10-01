import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class FairLock implements Lock{
    AtomicInteger flag;
    AtomicInteger guard;
    List<Thread> queue;

    public FairLock() {
        this.flag = new AtomicInteger(0);
        this.guard = new AtomicInteger(0);
        this.queue = new ArrayList<>();
    }
    
    private boolean isLockHolder() {
    	return queue.get(0).equals(Thread.currentThread());
    }

	@Override
	public void lock() {
		while(!this.guard.compareAndSet(0, 1));
		this.queue.add(Thread.currentThread());
		if (!this.flag.compareAndSet(0, 1)) {
			this.guard.set(0);
			LockSupport.park();
		}
		this.guard.set(0);
	}

	@Override
	public void unlock() {
		while(!this.guard.compareAndSet(0, 1));
		if (this.flag.get() == 1 && isLockHolder()) {
			this.queue.remove(0);
			if (!queue.isEmpty())
				LockSupport.unpark(queue.get(0));
			else
				this.flag.set(0);
		}
		this.guard.set(0);
	}
}
