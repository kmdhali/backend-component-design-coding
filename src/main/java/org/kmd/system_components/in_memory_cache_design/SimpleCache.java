package org.kmd.system_components.in_memory_cache_design;

interface SimpleCache<K, V> {

    void put(K key, V value);
    void put(K key, V value, long ttl);
    V get(K key);

}
