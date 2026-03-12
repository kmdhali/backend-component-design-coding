package org.kmd.system_components.lru_cache_design.linkedlist;

import lombok.Data;

@Data
public class Node<K, V> {
    K key;
    V value;
    Node<K, V> prev;
    Node<K, V> next;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
        next = null;
        prev = null;
    }

//    Node(K key, V value, Node<K, V> next, Node<K, V> prev) {
//        this.key = key;
//        this.value = value;
//        this.next = next;
//        this.prev = prev;
//    }

}
