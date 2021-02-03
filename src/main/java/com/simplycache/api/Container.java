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

    /**
     * Check if provided Key already exists in the Cache
     * @param key
     * @return true if key exists
     */
    boolean contains(K key);

    /**
     * Remove key from cache
     * @param key
     */
    void remove(K key);

    /**
     * Remove all keys in cache
     */
    void clearCache();


    default boolean isEmpty(){
      return size() == 0;
    }
}
