import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LockVar {
    AtomicInteger flag;
    AtomicInteger guard;
    List<Thread> queue;

    public LockVar() {
        this.flag = new AtomicInteger(0);
        this.guard = new AtomicInteger(0);
        this.queue = new ArrayList<>();
    }

    public Thread getThread() {
        return this.queue.remove(0);
    }

    public void pushThread(Thread t) {
        this.queue.add(t);
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
