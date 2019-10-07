import java.util.*;

import static java.lang.Thread.sleep;

public class CacheMap<K, V> implements Map<K, V> {
    private final int cacheSize;
    private final int timeoutSecs;
    private volatile Map<K, V> dbMap;
    private volatile long lastUpdateTime;
    private volatile Map<K, V> cache;

    CacheMap(int cacheSize, int timeoutSecs, Map<K, V> dbMap) {
        this.dbMap = dbMap;
        this.cacheSize = cacheSize;
        this.timeoutSecs = timeoutSecs;
        this.cache = new HashMap<>();
        this.lastUpdateTime = new Date().getTime();

        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    sleep(timeoutSecs * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateCache();
            }
        });

//        updateThread.start();
//        try {
//            updateThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    protected void updateCache() {
        synchronized (this) {
            this.dbMap.putAll(this.cache);
            this.cache.clear();
        }
        System.out.println("db updated");
    }

    public void clear() {
        synchronized (this) {
            this.dbMap.clear();
            this.cache.clear();
        }
    }

    public boolean containsKey(Object key) {
        synchronized (this) {
            boolean cacheResult = this.cache.containsKey(key);
            if (!cacheResult) {
                cacheResult = this.dbMap.containsKey(key);
            }
            return cacheResult;
        }
    }

    public V get(Object key) {
        synchronized (this) {
            V result = this.cache.get(key);
            if (result == null) {
                result = this.dbMap.get(key);
            }
            return result;
        }
    }

    public V put(K key, V value) {
        synchronized (this) {
            this.cache.put(key, value);
            if (this.cache.size() == this.cacheSize) {
                this.updateCache();
            }
        }
        return value;
    }

    public boolean isEmpty() {
        synchronized (this) {
            return this.cache.isEmpty() && this.dbMap.isEmpty();
        }
    }

    public V remove(Object key) {
        V removedValue = null;
        synchronized (this) {
            removedValue = this.cache.remove(key);
            if (removedValue == null) {
                removedValue = this.dbMap.remove(key);
            }
        }
        return removedValue;
    }

    public int size() {
        synchronized (this) {
            return this.cache.size() + this.dbMap.size();
        }
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

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

    /**
     * Object that will update the Cache every N seconds, given the time defined at the
     * constructor.
     */
    public class CacheUpdater implements Runnable {
        @Override
        public void run() {
            while(true) {
                try {
                    sleep(timeoutSecs * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateCache();
                System.out.println("db updated");
            }
        }
    }
}