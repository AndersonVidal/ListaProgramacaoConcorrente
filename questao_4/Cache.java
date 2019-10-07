import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {
    private volatile long lastUpdateTime;
    private Map<K, V> cacheMap;
    private static int maxTime;

    public Cache(Map<K, V> cacheMap, int maxTime) {
        this.cacheMap = new HashMap<>();
        this.cacheMap.putAll(cacheMap);
        this.maxTime = maxTime;
    }

    public void update(Map<K, V> cache) {
        if (needsUpdate()) {
            this.cacheMap = new HashMap<>();
            this.cacheMap.putAll(cache);
            this.lastUpdateTime = new Date().getTime();
        }
    }

    public boolean needsUpdate() {
        long actualTime = new Date().getTime();
        return (actualTime - lastUpdateTime) >= (this.maxTime * 1000);
    }

    public V get(Object key) {
        return this.cacheMap.get(key);
    }

    public boolean containsKey(Object key) {
        return this.cacheMap.containsKey(key);
    }

    public boolean isEmpty() {
        return this.cacheMap.isEmpty();
    }

    public int size() {
        return this.cacheMap.size();
    }
}
