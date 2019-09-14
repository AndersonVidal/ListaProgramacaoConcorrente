import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import java.lang.Thread;

class LockVar {
    Integer flag;
    Integer guard;
    List<Thread> queue;
    
    public LockVar() {
        this.flag = 0;
        this.guard = 0;
        this.queue = new ArrayList<>();
    }

    Thread getThread() { return this.queue.remove(0); }
    void pushThread(Thread t) { this.queue.add(t); }
}

class Lock {
    public void lock(LockVar varL) {
        int test;
        synchronized (varL.flag) {
            if (varL.flag == 1) test = 1;
            else {
                test = 0;
                varL.flag = 1;
            }
        }

        if (test == 1) {
                Thread current = Thread.currentThread();
            synchronized (varL.guard) {
                varL.pushThread(current);
                LockSupport.park();
            }
        }
    }

    public void unlock(LockVar varL) {
        boolean test;
        synchronized (varL.flag) {
            if (test = varL.queue.isEmpty()) {
               varL.flag = 0;
            }
        }
        if (!test) {
            synchronized (varL.guard) {
                Thread wakeup = varL.getThread();
                LockSupport.unpark(wakeup);
            }
        }
    }
}

public class Lock_impl {
    private static Integer cont = 0;
    private static Lock lock = new Lock();
    private static LockVar var = new LockVar();

    private static class CounterThread implements Runnable {
        @Override
        public void run() {
            lock.lock(var);
            for (int i=0; i<10000; i++) cont++;
            lock.unlock(var);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CounterThread c = new CounterThread();
        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c);
        Thread t3 = new Thread(c);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(cont);
    }
}