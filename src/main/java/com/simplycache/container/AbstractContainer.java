package com.simplycache.container;

import java.util.Map;

/**
 * @author anuradhaj Date: 2/4/21
 * Abstract Container which contains default implementations
 */
public abstract class AbstractContainer<K,V> implements Container<K,V> {

    protected final Map<K,V> container;

    AbstractContainer(Map<K, V> container){
      this.container = container;
    }

    @Override
    public V put(K key, V val) {
      return container.put(key, val);
    }

    @Override
    public V get(K key) {
      return container.get(key);
    }

    @Override
    public int size() {
      return container.size();
    }

    @Override
    public boolean containsKey(K key) {
      return container.containsKey(key);
    }

    @Override
    public void remove(K key) {
      container.remove(key);
    }

    @Override
    public void clear() {
      container.clear();
    }

    public K getKeyToReplace(){
      return container.entrySet().iterator().next().getKey();
    }
}
