import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConcurrentMapImpl<K, V> implements Map<K, V> {
    private final int maxSize;
    private final int maxTime;
    private volatile Map<K, V> database;
    private volatile boolean usingCache = false;
    private Cache<K, V> cache;

    ConcurrentMapImpl(Map<K, V> database, int maxSize, int maxTime) {
        this.database = database;
        this.maxSize = maxSize;
        this.maxTime = maxTime;
        this.cache = new Cache(database, maxTime);
    }

    public void clear() {
        synchronized (this) {
            this.database = new HashMap<K, V>();
        }
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    public boolean containsKey(Object key) {
        boolean cacheStatus = false;
        synchronized (this) {
            cacheStatus = this.usingCache;
        }
        if (cacheStatus) {
            return this.cache.containsKey(key);
        } else {
            return this.database.containsKey(key);
        }

    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    public V get(Object key) {
        boolean cacheStatus = false;
        synchronized (this) {
             cacheStatus = this.usingCache;
        }
        if (cacheStatus) {
            return this.cache.get(key);
        } else {
            return this.database.get(key);
        }

    }

    public boolean isEmpty() {
        boolean cacheStatus = false;
        synchronized (this) {
            cacheStatus = this.usingCache;
        }

        if (cacheStatus) {
            return this.cache.isEmpty();
        } else {
            return this.database.isEmpty();
        }

    }

    public V put(K key, V value) {
        synchronized (this) {
            while(this.usingCache) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.usingCache = true;
        }

        this.database.put(key, value);

        synchronized (this) {
            this.cache.update(this.database);
            this.usingCache = false;
            this.notifyAll();
        }
        return value;
    }

    public V remove(Object key) {
//        return this.database.remove(key);
        synchronized (this) {
            while(this.usingCache) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.usingCache = true;
        }

        V result = this.database.remove(key);

        synchronized (this) {
            this.cache.update(this.database);
            this.usingCache = false;
            this.notifyAll();
        }

        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    public int size() {
        boolean cacheStatus = false;
        synchronized (this) {
            cacheStatus = this.usingCache;
        }
        if (cacheStatus) {
            return this.cache.size();
        } else {
            return this.database.size();
        }

    }
}