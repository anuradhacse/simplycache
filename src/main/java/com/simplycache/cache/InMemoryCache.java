package com.simplycache.cache;

import com.simplycache.evictionpolicy.EvictionPolicy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author anuradhaj Date: 2/2/21
 * InMemory container implementation
 * This class is not thread safe
 */
public class InMemoryCache<K, V> extends AbstractCache<K, V, V> {

    private static final Logger LOGGER = Logger.getLogger(InMemoryCache.class.getName());

    public InMemoryCache(int size, EvictionPolicy evictionPolicy) {
     super(size, evictionPolicy);
    }

    @Override
    public void put(K key, V val) {
      LOGGER.log(Level.INFO, "Put an object with key {0} into InMemory cache", key);
      container.put(key, val);
    }

    @Override
    public V get(K key) {
      return container.get(key);
    }
}
