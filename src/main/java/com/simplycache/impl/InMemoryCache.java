package com.simplycache.impl;

import static com.simplycache.common.Error.INITIAL_SIZE_ERROR;

import com.simplycache.api.Container;
import com.simplycache.evictionpolicy.EvictionPolicy;

/**
 * @author anuradhaj Date: 2/2/21
 * InMemory cache implementation, Not thread safe
 */
public class InMemoryCache<K, V> extends AbstractCache<K, V> {

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
