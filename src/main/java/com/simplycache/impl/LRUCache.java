package com.simplycache.impl;

import com.simplycache.api.Cache;
import com.simplycache.api.Container;
import java.util.LinkedHashMap;

/**
 * @author anuradhaj Date: 2/3/21
 * LRU Cache implementation
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> implements Container<K,V> {

  private static final float LOAD_FACTOR = 0.75f;
  private static final boolean ACCESS_ORDER = true;
  private final int capacity;

  public LRUCache(int initialCapacity) {
    super(initialCapacity, LOAD_FACTOR, ACCESS_ORDER);
    this.capacity = initialCapacity;
  }

  @Override
  protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
    return size() > this.capacity;
  }
}
