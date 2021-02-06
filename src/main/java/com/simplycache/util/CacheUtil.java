package com.simplycache.util;

import com.simplycache.cache.CacheException;
import com.simplycache.common.ErrorCodes;
import com.simplycache.container.Container;
import com.simplycache.container.LFUContainer;
import com.simplycache.container.LRUContainer;
import com.simplycache.evictionpolicy.EvictionPolicy;

/**
 * @author anuradhaj Date: 2/6/21
 * Utility methods for cache implementation
 */
public final class CacheUtil {

    private CacheUtil() {
    }

    public static <K, V> Container<K, V> getContainer(EvictionPolicy evictionPolicy, int size) {

      if(size <= 0) {
        throw new CacheException("initial size should be greater than 0", ErrorCodes.INITIAL_CACHE_SIZE_ERROR);
      }

      switch (evictionPolicy) {
        case LFU:
          return new LFUContainer<>(size);
        case LRU:
        default:
          return new LRUContainer<>(size);
      }
    }
}
