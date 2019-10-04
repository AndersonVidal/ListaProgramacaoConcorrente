import java.lang.Thread;
import java.util.HashMap;
import java.util.Map;

public class TestQuest4 {
    public static void main(String[] args) throws InterruptedException {
//        CounterRoutine c = new CounterRoutine(0, 100000);
//        Thread t1 = new Thread(c);
//        Thread t2 = new Thread(c);
//        Thread t3 = new Thread(c);
//        t1.start();
//        t2.start();
//        t3.start();
//        t1.join();
//        t2.join();
//        t3.join();
//        System.out.println(c.getCount());
        Map<String, Integer> database = new HashMap<>();
        Map<String, Integer> concMap = new ConcurrentMapImpl<>(database, 5, 1);
        concMap.put("a", 1);
        int result = concMap.get("a");
        System.out.println(result);
        System.out.println("all ok");
    }
}