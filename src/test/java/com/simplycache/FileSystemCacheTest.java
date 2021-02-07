package com.simplycache;

import com.simplycache.cache.Cache;
import com.simplycache.evictionpolicy.EvictionPolicy;
import com.simplycache.cache.FileSystemCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author anuradhaj Date: 2/4/21
 */
public class FileSystemCacheTest {

  @Test
  public void testFileSystemLRUCache(){
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

    cache.remove("A");
    Assertions.assertFalse(cache.contains("A"));
    Assertions.assertNull(cache.get("A"));

    cache.clearCache();
    Assertions.assertTrue(cache.isEmpty());
  }

  @Test
  public void testFileSystemLFUCache(){

    Cache<String, Integer> cache = new FileSystemCache<>(3, EvictionPolicy.LFU);

    cache.put("A", 1);
    cache.put("B", 2);
    cache.put("C", 3);

    Assertions.assertEquals(3, cache.size());
    Assertions.assertFalse(cache.isEmpty());

    cache.get("A");
    cache.get("B");

    cache.put("D", 4);

    //when inserting D, LFU element is "C", Hence it will be removed
    Assertions.assertFalse(cache.contains("C"));
    Assertions.assertNull(cache.get("C"));

    Assertions.assertEquals(1, cache.get("A"));
    Assertions.assertEquals(2, cache.get("B"));
    Assertions.assertEquals(4, cache.get("D"));

    cache.remove("A");
    Assertions.assertFalse(cache.contains("A"));
    Assertions.assertNull(cache.get("A"));

    cache.clearCache();
    Assertions.assertTrue(cache.isEmpty());

    cache.put("C", 1);
    cache.put("D", 2);
    cache.put("E", 3);

    cache.put("C", 3);
    cache.put("D", 4);
    cache.put("D", 5);

    Assertions.assertEquals(3, cache.size());
    Assertions.assertEquals(3, cache.get("C"));
    Assertions.assertEquals(5, cache.get("D"));
    Assertions.assertEquals(3, cache.get("E"));

    //let's put F, then LFU key E will get replaced
    cache.put("F", 6);
    Assertions.assertFalse(cache.contains("E"));
    Assertions.assertNull(cache.get("E"));

    //let's put G, then LFU key F will get replaced
    cache.put("G", 7);
    Assertions.assertFalse(cache.contains("F"));
    Assertions.assertNull(cache.get("F"));
  }

}
