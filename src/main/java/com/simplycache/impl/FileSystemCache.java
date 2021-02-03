package com.simplycache.impl;

import com.simplycache.evictionpolicy.EvictionPolicy;

/**
 * @author anuradhaj Date: 2/3/21
 */
public class FileSystemCache<K, V> extends AbstractCache<K, V> {

    public FileSystemCache(int size, EvictionPolicy evictionPolicy) {
      super(size, evictionPolicy);
    }

    @Override
    public void put(K key, V val) {
      //file manupulation
      cache.put(key, val);
    }

    @Override
    public V get(K key) {
      return null;
    }

    @Override
    public int size() {
      return 0;
    }
}
