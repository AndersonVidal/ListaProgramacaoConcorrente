import java.util.Map;
import java.util.Random;

import static java.lang.Thread.sleep;

public class ConcurrentGetter implements Runnable {
    private Map cacheMap;

    public ConcurrentGetter(Map cacheMap) {
        this.cacheMap = cacheMap;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Integer key = new Random().nextInt() % 10;
            if (key < 0) {
                key *= -1;
            }
            System.out.println("[GET]" + this.cacheMap.get(key.toString()) + " got from key: " + key);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
