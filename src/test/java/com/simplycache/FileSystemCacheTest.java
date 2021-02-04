package com.simplycache;

import com.simplycache.api.Cache;
import com.simplycache.evictionpolicy.EvictionPolicy;
import com.simplycache.impl.FileSystemCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author anuradhaj Date: 2/4/21
 */
public class FileSystemCacheTest {

  @Test
  public void testFileSystemCache(){
    Cache<String, Integer> cache = new FileSystemCache<>(3, EvictionPolicy.LRU);

    cache.put("A", 1);
    cache.put("B", 2);
    cache.put("C", 3);

    Assertions.assertEquals(3, cache.size());
    Assertions.assertFalse(cache.isEmpty());

    Assertions.assertEquals(1, cache.get("A"));
    Assertions.assertEquals(2, cache.get("B"));
    Assertions.assertEquals(3, cache.get("C"));

    cache = new FileSystemCache<>(3, EvictionPolicy.LRU);

    cache.put("A", 1);
    cache.put("B", 2);
    cache.put("C", 3);

    cache.put("D", 4);

    Assertions.assertEquals(3, cache.size());
    //when inserting D, LRU element is "A", Hence it will be removed
    Assertions.assertFalse(cache.contains("A"));
    Assertions.assertNull(cache.get("A"));

    Assertions.assertEquals(2, cache.get("B"));
    Assertions.assertEquals(3, cache.get("C"));
    Assertions.assertEquals(4, cache.get("D"));

    cache = new FileSystemCache<>(3, EvictionPolicy.LRU);

    cache.put("A", 1);
    cache.put("B", 2);
    cache.put("C", 3);

    cache.get("A");
    cache.get("B");

    cache.put("D", 4);
    //when inserting D, LRU element is "C", Hence it will be removed
    Assertions.assertFalse(cache.contains("C"));
    Assertions.assertNull(cache.get("C"));

    Assertions.assertEquals(1, cache.get("A"));
    Assertions.assertEquals(2, cache.get("B"));
    Assertions.assertEquals(4, cache.get("D"));
  }

}
