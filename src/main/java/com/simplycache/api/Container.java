package com.simplycache.api;

/**
 * @author anuradhaj Date: 2/3/21
 * Cache data holding container with eviction policy support
 * See implementing classes {@link com.simplycache.impl.LRUCache}}, {@link com.simplycache.impl.LFUCache}
 */
public interface Container<K,V> {

    V put(K key, V val);
    V get(K key);
    int size();
}
