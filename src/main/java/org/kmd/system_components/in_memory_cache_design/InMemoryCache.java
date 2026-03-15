package org.kmd.system_components.in_memory_cache_design;

import java.util.Optional;
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

    /**
     * Implementation with Java 8 Optional
     */

    @Override
    public Optional<V> get_2(K key) {

        CacheEntry<V> vCacheEntry = this.cacheEntries.get(key);

        if (vCacheEntry != null) {
            if (!vCacheEntry.isExpired()) {

                V cacheVal = vCacheEntry.getValue();
                return Optional.of(cacheVal);
            } else {
                return Optional.empty();

            }
        } else {
            System.out.printf("The key %s does nit exists in cache", key);
            return Optional.empty();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        SimpleCache<String, String> cache = new InMemoryCache<>();

        cache.put("kmd", "kmd-val");
        cache.put("kmd1", "kmd1-val");

        Thread.sleep(4000);
        cache.get("kmd");
        cache.get("xxx");

        /* Using Optional */
        Optional<String> value = cache.get_2("kmd");
        if (value.isPresent()) {
            System.out.printf("The key kmd is present, value = %s", value.get());
        }else{
            System.out.println("the key kmd does not exists ");
        }

        //Another way to use , use a conusmer

        System.out.println("\nanother test");
        Optional<String> value1 = cache.get_2("kmd");
        value1.ifPresent(System.out::println);
    }

}


