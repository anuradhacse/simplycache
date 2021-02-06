package com.simplycache.container;

import java.util.Map;

/**
 * @author anuradhaj Date: 2/3/21
 * Cache data holding container data structure with eviction policy support
 * See implementing classes {@link LRUContainer}}, {@link LFUContainer}
 */
public interface Container<K,V> {

  /**
   * put a key, value to the container. if container size exceeds,
   * key will be replaced based on eviction policy
   * @param key key
   * @param val value
   * @return key
   */
    V put(K key, V val);

  /**
   * Get value of key. Will update the access of the key
   * @param key
   * @return
   */
    V get(K key);

    int size();

    /**
     * Check if provided Key already exists in the Cache
     * @param key
     * @return true if key exists
     */
    boolean containsKey(K key);

    /**
     * Remove key from container
     * @param key
     */
    void remove(K key);

    /**
     * Remove all keys in container
     */
    void clear();

    K getKeyToReplace();

    default boolean isEmpty(){
      return size() == 0;
    }
}
