package com.simplycache.cache;

/**
 * @author anuradhaj Date: 2/2/21
 * Interface for defining cache methods.
 * See Implementing classes {@link InMemoryCache}, {@link FileSystemCache}
 */
public interface Cache<K, V> {

    /**
     * API for putting a value into the cache
     * @param key key
     * @param val value
     * @return true if operation successful
     */
    void put(K key, V val);

    /**
     * API for getting a value specified by a key
     * @param  key
     * @return value for the given key
     */
    V get(K key);

    /**
     * Get number of items in cache
     * @return size
     */
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
