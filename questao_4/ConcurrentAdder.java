import java.util.Map;
import java.util.Random;

import static java.lang.Thread.sleep;

public class ConcurrentAdder implements Runnable {
    private Map cacheMap;

    public ConcurrentAdder(Map cacheMap) {
        this.cacheMap = cacheMap;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Integer key = new Random().nextInt() % 10;
            if (key < 0) {
                key *= -1;
            }
            int value = new Random().nextInt();
            this.cacheMap.put(key.toString(), value);
            System.out.println("[ADD] put key: " + key + " value: " + value);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
