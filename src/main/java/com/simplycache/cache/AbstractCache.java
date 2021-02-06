package com.simplycache.cache;

import com.simplycache.container.Container;
import com.simplycache.evictionpolicy.EvictionPolicy;
import com.simplycache.util.CacheUtil;

/**
 * @author anuradhaj Date: 2/3/21
 */
abstract class AbstractCache<K, V, T> implements Cache<K, V> {

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
      container.remove(key);
    }

    @Override
    public void clearCache() {
      container.clear();
    }

    protected boolean isCacheFull(){
      return initialSize == container.size();
    }
}
