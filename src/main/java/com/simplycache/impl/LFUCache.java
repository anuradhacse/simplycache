package com.simplycache.impl;

import static com.simplycache.common.Constants.FREQUENCY;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author anuradhaj Date: 2/3/21
 */
public class LFUCache<K,V> extends AbstractContainer<K, V> {

    private final SortedMap<K,Long> sortedMap;
    private final Map<K, Long> frequencyMap;
    private final int initialCapacity;

    public LFUCache(int size) {
      super(new HashMap<>(size));
      initialCapacity = size;
      frequencyMap = new HashMap<>();
      sortedMap = new TreeMap<>((k1, k2)-> {
        return frequencyMap.get(k1).compareTo(frequencyMap.get(k2));
      });
    }

    @Override
    public V put(K key, V val) {
      if(cache.size() < initialCapacity || cache.containsKey(key)){
        if(frequencyMap.containsKey(key)){
          frequencyMap.put(key, frequencyMap.get(key) + FREQUENCY);
        }
        frequencyMap.put(key, FREQUENCY);
        return cache.put(key, val);
      }
      K replacingKey = getReplacingKey();
      cache.remove(replacingKey);
      frequencyMap.remove(replacingKey);
      return cache.put(key, val);
    }

    @Override
    public V get(K key) {
      if(frequencyMap.containsKey(key)){
        frequencyMap.put(key, frequencyMap.get(key) + FREQUENCY);
      }
      return cache.get(key);
    }

    private K getReplacingKey(){
      sortedMap.clear();
      sortedMap.putAll(frequencyMap);
      return sortedMap.firstKey();
    }
}
