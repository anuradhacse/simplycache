package com.simplycache.impl;

import com.simplycache.api.Cache;
import java.util.LinkedHashMap;

/**
 * @author anuradhaj Date: 2/3/21
 * LRU Cache implementation which uses a Linked HashMap
 */
public class LRUContainer<K, V> extends AbstractContainer<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private static final boolean ACCESS_ORDER = true;

    LRUContainer(int initialCapacity) {
      super( new LinkedHashMap<K, V>(initialCapacity, LOAD_FACTOR, ACCESS_ORDER){
        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
          return size() > initialCapacity;
        }
      });
    }
}
