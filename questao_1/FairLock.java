import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class FairLock implements Lock{
	private AtomicInteger flag;
    private AtomicInteger guard;
    private List<Thread> queue;
    private long flagOwner;

    public FairLock() {
    	this.flag = new AtomicInteger(0);
        this.guard = new AtomicInteger(0);
        this.queue = new ArrayList<>();
    }
    
    private boolean unlockPermission() {
    	return this.flag.get() == 1 && Thread.currentThread().getId() == this.flagOwner;
    }

	@Override
	public void lock() {
		Thread curThread = Thread.currentThread();
		while (!guard.compareAndSet(0, 1)); //Get guard
		
		if (!this.flag.compareAndSet(0, 1)) { // If not first in queue, sleep
			this.queue.add(curThread);
			guard.set(0);
			LockSupport.park();
			while (curThread.getId() != this.flagOwner) { //Dealing with spurious wakeup.
				LockSupport.park();
			}
		} else {
			this.flagOwner = curThread.getId();
		}
		guard.set(0);
	}

	@Override
	public void unlock() {
		while (!guard.compareAndSet(0, 1)); //Get guard
		
		if (unlockPermission()) { //Permission to unlock
			if (!this.queue.isEmpty()) {
				Thread next = this.queue.remove(0);
				this.flagOwner = next.getId();
				LockSupport.unpark(next); // If someone in the queue, wake it! Waked thread should release guard.
				return;
			} else {
				this.flagOwner = 0;
				this.flag.set(0);
				this.guard.set(0);
			}
		} else {
			this.guard.set(0); //Couldn't get permission to unlock, release guard.
		}
	}
}
