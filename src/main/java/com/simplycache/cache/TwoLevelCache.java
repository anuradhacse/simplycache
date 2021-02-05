package com.simplycache.cache;

import com.simplycache.container.Container;
import com.simplycache.container.LFUContainer;
import com.simplycache.container.LRUContainer;
import com.simplycache.evictionpolicy.EvictionPolicy;
import java.io.Serializable;

/**
 * @author anuradhaj Date: 2/4/21
 */
public class TwoLevelCache<K, V extends Serializable> implements Cache<K, V> {

  private final InMemoryCache<K, V> level1Cache;
  private final FileSystemCache<K, V> level2Cache;
  private final Container<K, V> globalContainer;

  public TwoLevelCache(int inMemoryCacheSize, int fileCacheSize, EvictionPolicy evictionPolicy){
    level1Cache = new InMemoryCache<>(inMemoryCacheSize, evictionPolicy);
    level2Cache =  new FileSystemCache<>(fileCacheSize, evictionPolicy);
    switch (evictionPolicy) {
      case LFU:
        globalContainer = new LFUContainer<>(inMemoryCacheSize+fileCacheSize+1);
        break;
      case LRU:
      default:
        globalContainer = new LRUContainer<>(inMemoryCacheSize+fileCacheSize+1);
    }
  }

  @Override
  public void put(K key, V val) {

    if(level1Cache.contains(key) || !level1Cache.isCacheFull()){
      level1Cache.put(key, val);
      globalContainer.put(key, val);
    } else if(level2Cache.contains(key) || !level2Cache.isCacheFull()){
      level2Cache.put(key, val);
      globalContainer.put(key, val);
    }

    //get the LRU or LFU element from global containers
    K keyToReplace = globalContainer.getKeyToReplace();

    if(level1Cache.contains(keyToReplace)){
      level1Cache.remove(keyToReplace);
      level1Cache.put(key, val);
    } else if(level2Cache.contains(keyToReplace)){
      level2Cache.remove(keyToReplace);
      level2Cache.put(key, val);
    }

    globalContainer.remove(keyToReplace);
  }

  @Override
  public V get(K key) {
    return null;
  }

  @Override
  public int size() {
    return level1Cache.size() + level2Cache.size();
  }

  @Override
  public boolean contains(K key) {
    return level1Cache.contains(key) || level2Cache.contains(key);
  }

  @Override
  public void remove(K key) {

  }

  @Override
  public void clearCache() {
    level1Cache.clearCache();
    level2Cache.clearCache();
  }
}
