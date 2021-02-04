package com.simplycache.cache;

import com.simplycache.evictionpolicy.EvictionPolicy;

/**
 * @author anuradhaj Date: 2/2/21
 * InMemory cache implementation
 * This class is not thread safe
 */
public class InMemoryCache<K, V> extends AbstractCache<K, V, V> {

    public InMemoryCache(int size, EvictionPolicy evictionPolicy) {
     super(size, evictionPolicy);
    }

    @Override
    public void put(K key, V val) {
        cache.put(key, val);
    }

    @Override
    public V get(K key) {
      return cache.get(key);
    }
}