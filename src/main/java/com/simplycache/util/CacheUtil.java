package com.simplycache.util;

import static com.simplycache.common.Error.INITIAL_SIZE_ERROR;
import static com.simplycache.evictionpolicy.EvictionPolicy.LFU;
import static com.simplycache.evictionpolicy.EvictionPolicy.LRU;

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
        throw new IllegalArgumentException(INITIAL_SIZE_ERROR);
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
