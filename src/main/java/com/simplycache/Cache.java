package com.simplycache;

/**
 * @author anuradhaj Date: 2/2/21
 * Interface for defining cache methods.
 */
public interface Cache<K, V> {

  /**
   * API for putting a value into the cache
   * @param K key
   * @param V value
   * @return true if operation successful
   */
  boolean put(Object K, Object V);

  /**
   * API for getting a value specified by a key
   * @param K key
   * @return value for the given key
   */
  V get(Object K);
}
