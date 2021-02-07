package com.simplycache.cache;

import com.simplycache.evictionpolicy.EvictionPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author anuradhaj Date: 2/2/21
 * InMemory container implementation
 * This class is not thread safe
 */
public class InMemoryCache<K, V> extends AbstractCache<K, V, V> {

    private static final Logger LOGGER = LogManager.getLogger(InMemoryCache.class);


    public InMemoryCache(int size, EvictionPolicy evictionPolicy) {
     super(size, evictionPolicy);
    }

    @Override
    public void put(K key, V val) {
      LOGGER.info("Putting an object with key {} into InMemory cache", key);
      container.put(key, val);
    }

    @Override
    public V get(K key) {
      LOGGER.info("Getting an object with key {} from InMemory cache", key);
      return container.get(key);
    }
}
