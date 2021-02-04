package com.simplycache.impl;

import static com.simplycache.common.Error.INITIAL_SIZE_ERROR;

import com.simplycache.api.Cache;
import com.simplycache.container.Container;
import com.simplycache.evictionpolicy.EvictionPolicy;

/**
 * @author anuradhaj Date: 2/3/21
 */
abstract class AbstractCache<K, V, T> implements Cache<K, V> {

     Container<K, T> cache;

     AbstractCache(int size, EvictionPolicy evictionPolicy){
      if(size <= 0) {
        throw new IllegalArgumentException(INITIAL_SIZE_ERROR);
      }

      switch (evictionPolicy) {
        case LFU:
          cache = new LFUContainer<>(size);
          break;
        case LRU:
          cache = new LRUContainer<>(size);
          break;
        default:
          cache = new LRUContainer<>(size);
          break;
      }
    }

    @Override
    public int size() {
      return cache.size();
    }

    @Override
    public boolean contains(K key) {
      return cache.containsKey(key);
    }

    @Override
    public void remove(K key) {
      cache.remove(key);
    }

    @Override
    public void clearCache() {
      cache.clear();
    }
}
