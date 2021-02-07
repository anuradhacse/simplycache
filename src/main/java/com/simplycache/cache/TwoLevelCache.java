package com.simplycache.cache;

import com.simplycache.container.Container;
import com.simplycache.evictionpolicy.EvictionPolicy;
import com.simplycache.util.CacheUtil;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * @author anuradhaj Date: 2/4/21
 */
public class TwoLevelCache<K, V extends Serializable> implements Cache<K, V> {

    private static final Logger LOGGER = LogManager.getLogger(TwoLevelCache.class);

    private final InMemoryCache<K, V> level1Cache;
    private final FileSystemCache<K, V> level2Cache;
    private final Container<K, V> globalContainer;

    public TwoLevelCache(int inMemoryCacheSize, int fileCacheSize, EvictionPolicy evictionPolicy){
      level1Cache = new InMemoryCache<>(inMemoryCacheSize, evictionPolicy);
      level2Cache =  new FileSystemCache<>(fileCacheSize, evictionPolicy);
      int globalContainerSize = inMemoryCacheSize  + fileCacheSize;
      globalContainer = CacheUtil.getContainer(evictionPolicy, globalContainerSize);
    }

    @Override
    public void put(K key, V val) {
      if (level1Cache.contains(key) || !level1Cache.isCacheFull()) {
        level1Cache.put(key, val);
        globalContainer.put(key, val);
      } else if (level2Cache.contains(key) || !level2Cache.isCacheFull()) {
        level2Cache.put(key, val);
        globalContainer.put(key, val);
      } else {
        K keyToReplace = globalContainer.getKeyToReplace();
        if (level1Cache.contains(keyToReplace)) {
          level1Cache.remove(keyToReplace);
          level1Cache.put(key, val);
        } else if (level2Cache.contains(keyToReplace)) {
          level2Cache.remove(keyToReplace);
          level2Cache.put(key, val);
        }
        globalContainer.remove(keyToReplace);
        globalContainer.put(key, val);
      }
    }

    @Override
    public V get(K key) {
      if (level1Cache.contains(key)) {
        globalContainer.get(key);
        return level1Cache.get(key);
      } else if (level2Cache.contains(key)) {
        globalContainer.get(key);
        return level2Cache.get(key);
      } else {
        LOGGER.warn("key {} does not exists", key);
        return null;
      }
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
      if(level1Cache.contains(key)){
        level1Cache.remove(key);
        globalContainer.remove(key);
      } else if(level2Cache.contains(key)){
        level2Cache.remove(key);
        globalContainer.remove(key);
      } else {
        LOGGER.warn("key {} does not exists", key);
      }
    }

    @Override
    public void clearCache() {
      level1Cache.clearCache();
      level2Cache.clearCache();
      globalContainer.clear();
    }
}
