package com.simplycache.impl;

import static com.simplycache.common.Error.INITIAL_SIZE_ERROR;
import static com.simplycache.evictionpolicy.EvictionPolicy.LFU;
import static com.simplycache.evictionpolicy.EvictionPolicy.LRU;

import com.simplycache.api.Cache;
import com.simplycache.api.Container;
import com.simplycache.evictionpolicy.EvictionPolicy;

/**
 * @author anuradhaj Date: 2/3/21
 */
abstract class AbstractCache<K, V> implements Cache<K, V> {
  //put common code of Inmemory and FileSystem
   Container<K, V> cache;

   AbstractCache(int size, EvictionPolicy evictionPolicy){
    if(size <= 0) {
      throw new IllegalArgumentException(INITIAL_SIZE_ERROR);
    }

    switch (evictionPolicy) {
      case LFU:
        cache = new LRUCache<>(size);
        break;
      case LRU:
        cache = new LRUCache<>(size);
        break;
      default:
        cache = new LRUCache<>(size);
        break;
    }
  }
}
