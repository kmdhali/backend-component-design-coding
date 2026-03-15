package org.kmd.system_components.lru_cache_design;


import org.kmd.system_components.lru_cache_design.linkedlist.DoubleLinkedList;
import org.kmd.system_components.lru_cache_design.linkedlist.Node;

class LRU_Cache<K, V> implements SimpleCache<K, V> {

    private final long MAX_CACHE_SIZE = 5L;

    private final long defaultTTL = 5000L;
    private final int initialCacheSize = 5;


    private DoubleLinkedList<K, V> hashedLinkedList;


    public LRU_Cache() {
        hashedLinkedList = new DoubleLinkedList<>();
    }

    @Override
    public void put(K key, V value) {

        if (hashedLinkedList.getNodeLocatorMap().size() >= MAX_CACHE_SIZE) {
           // hashedLinkedList
        }
        hashedLinkedList.addOnFront(key, value);

    }

    @Override
    public void put(K key, V value, long ttl) {
        //@todo
    }


    @Override
    public V get(K key) {
        Node<K, V> kvNode = hashedLinkedList.getNodeLocatorMap().get(key);
        if (kvNode == null) {
            System.out.printf("key : %s is not found in the map", key);
            return null;
        } else {

            hashedLinkedList.moveToHead(kvNode);
            return kvNode.getValue();
        }

    }

    public void iterateFromHead() {
        hashedLinkedList.iterateFromHead();
    }

    public static void main(String[] args) throws InterruptedException {

        LRU_Cache<String, String> cache = new LRU_Cache<>();

        cache.put("kmd-1", "kmd-val-1");
        cache.put("kmd-2", "kmd-val-2");
        cache.put("kmd-3", "kmd-val-3");
        cache.put("kmd-4", "kmd-val-4");
        cache.put("kmd-5", "kmd-val-5");

        System.out.println("-------------------");
        cache.iterateFromHead();
        cache.get("kmd-4");
        System.out.println("-------------------");
        cache.iterateFromHead();
        cache.get("kmd-2");
        System.out.println("-------------------");
        cache.iterateFromHead();

        System.out.println("-------------------");
        cache.put("kmd-5", "kmd-val-5");
        cache.iterateFromHead();
    }

}


