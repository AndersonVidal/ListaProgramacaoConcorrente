import java.util.Map;
import java.util.Random;

import static java.lang.Thread.sleep;

public class ConcurrentRemover implements Runnable {
    private Map cacheMap;

    public ConcurrentRemover(Map cacheMap) {
        this.cacheMap = cacheMap;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            Integer key = new Random().nextInt() % 10;
            if (key < 0) {
                key *= -1;
            }
            Object result = this.cacheMap.remove(key.toString());
            System.err.println("[REMOVE] " + result + " removed with key: " + key);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
