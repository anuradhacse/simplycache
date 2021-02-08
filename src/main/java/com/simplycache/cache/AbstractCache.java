package com.simplycache.cache;

import com.simplycache.container.Container;
import com.simplycache.evictionpolicy.EvictionPolicy;
import com.simplycache.util.CacheUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author anuradhaj Date: 2/3/21
 */
abstract class AbstractCache<K, V, T> implements Cache<K, V> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractCache.class);

    protected Container<K, T> container;
    private final int initialSize;

     AbstractCache(int size, EvictionPolicy evictionPolicy){
      this.initialSize = size;
      container = CacheUtil.getContainer(evictionPolicy, size);
    }

    @Override
    public int size() {
      return container.size();
    }

    @Override
    public boolean contains(K key) {
      return container.containsKey(key);
    }

    @Override
    public void remove(K key) {
      LOGGER.info("Removing Key {}", key);
      container.remove(key);
    }

    @Override
    public void clearCache() {
      LOGGER.info("Cache is cleared");
      container.clear();
    }

    boolean isCacheFull(){
      return initialSize == container.size();
    }
}
