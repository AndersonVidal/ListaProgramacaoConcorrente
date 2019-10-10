import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class TestQuest4 {
    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> database = new HashMap<>();
        Map<String, Integer> concMap = new CacheMap<>(3, 1, database);
        Thread t1 = new Thread(new ConcurrentAdder(concMap));
        Thread t2 = new Thread(new ConcurrentGetter(concMap));
        Thread t3 = new Thread(new ConcurrentRemover(concMap));
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        sleep(1000);

        for (String key : database.keySet()) {
            System.out.println("( " + key + ", " + database.get(key) + ")");
        }
    }
}