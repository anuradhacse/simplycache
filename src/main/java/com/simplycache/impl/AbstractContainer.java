package com.simplycache.impl;

import com.simplycache.api.Container;
import java.util.Map;

/**
 * @author anuradhaj Date: 2/4/21
 */
public abstract class AbstractContainer<K,V> implements Container<K,V> {

    protected final Map<K,V> cache;

    AbstractContainer(Map<K, V> cache){
      this.cache = cache;
    }

    @Override
    public V put(K key, V val) {
      return cache.put(key, val);
    }

    @Override
    public V get(K key) {
      return cache.get(key);
    }

    @Override
    public int size() {
      return cache.size();
    }

    @Override
    public boolean containsKey(K key) {
      return cache.containsKey(key);
    }

    @Override
    public void remove(K key) {
      cache.remove(key);
    }

    @Override
    public void clear() {
      cache.clear();
    }
}
