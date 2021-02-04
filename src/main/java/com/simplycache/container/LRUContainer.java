package com.simplycache.container;

import java.util.LinkedHashMap;

/**
 * @author anuradhaj Date: 2/3/21
 * LRU Cache implementation which uses a LinkedHashMap
 * Keys are removed when size exeeds max size based on the LRU policy
 * LRU key is updated for every put and get
 */
public class LRUContainer<K, V> extends AbstractContainer<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private static final boolean ACCESS_ORDER = true;

    public LRUContainer(int initialCapacity) {
      super( new LinkedHashMap<K, V>(initialCapacity, LOAD_FACTOR, ACCESS_ORDER){
        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
          return size() > initialCapacity;
        }
      });
    }
}
