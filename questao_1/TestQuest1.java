import java.lang.Thread;

public class TestQuest1 {
    public static void main(String[] args) throws InterruptedException {
        CounterRoutine c = new CounterRoutine(0, 100000);
        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c);
        Thread t3 = new Thread(c);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(c.getCount());
    }
}