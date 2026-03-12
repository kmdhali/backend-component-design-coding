package org.kmd.system_components.in_memory_cache_design;

import java.util.concurrent.ConcurrentHashMap;

class InMemoryCache<K, V> implements SimpleCache<K, V> {


    private final long defaultTTL = 5000L;           //in millis
    private final long maxCacheSize = 1000L;
    private final int initialCacheSize = 100;


    private ConcurrentHashMap<K, CacheEntry<V>> cacheEntries;


    public InMemoryCache() {
        cacheEntries = new ConcurrentHashMap<>(initialCacheSize);
    }


    @Override
    public void put(K key, V value, long ttl) {
        cacheEntries.put(key, new CacheEntry<>(value, System.currentTimeMillis() + ttl));
    }


    @Override
    public void put(K key, V value) {
        cacheEntries.put(key, new CacheEntry<>(value, System.currentTimeMillis() + defaultTTL));

    }

    @Override
    public V get(K key) {
        CacheEntry<V> entry = cacheEntries.get(key);
        if (entry != null) {
            if (!entry.isExpired()) {
                System.out.println("Object exists , key : " + key + " returned the object ..");
                return entry.getValue();
            } else {
                System.out.println("Entry for : " + key + " expired ." + entry.getTtl());
                cacheEntries.remove(key);
                return null;
            }
        } else {
            System.out.println("The entry for key : " + key + " does not exists..");
            return null;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        SimpleCache<String, String> cache = new InMemoryCache<>();

        cache.put("kmd", "kmd-val");
        cache.put("kmd1", "kmd1-val");

        Thread.sleep(4000);
        cache.get("kmd");
        cache.get("xxx");

    }

}


