package com.simplycache;

import com.simplycache.cache.Cache;
import com.simplycache.cache.TwoLevelCache;
import com.simplycache.evictionpolicy.EvictionPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author anuradhaj Date: 2/6/21
 */
public class TwoLevelCacheTest {

  @Test
  public void testTwoLevelLRUCache(){
    Cache<Integer, String> twoLevelCache = new TwoLevelCache<>(3,3, EvictionPolicy.LRU);

    twoLevelCache.put(1, "A");
    twoLevelCache.put(2, "B");
    twoLevelCache.put(3, "C");

    twoLevelCache.put(4, "D");
    twoLevelCache.put(5, "E");
    twoLevelCache.put(6, "F");

    Assertions.assertEquals(6, twoLevelCache.size());
    Assertions.assertTrue(twoLevelCache.contains(1));
    Assertions.assertTrue(twoLevelCache.contains(2));
    Assertions.assertTrue(twoLevelCache.contains(3));
    Assertions.assertTrue(twoLevelCache.contains(4));
    Assertions.assertTrue(twoLevelCache.contains(5));
    Assertions.assertTrue(twoLevelCache.contains(6));

    twoLevelCache.remove(1);
    Assertions.assertNull(twoLevelCache.get(1));
    Assertions.assertFalse(twoLevelCache.contains(1));
    Assertions.assertEquals(5, twoLevelCache.size());

    twoLevelCache.clearCache();
    Assertions.assertTrue(twoLevelCache.isEmpty());

    twoLevelCache = new TwoLevelCache<>(3,3, EvictionPolicy.LRU);

    twoLevelCache.put(1, "A");
    twoLevelCache.put(2, "B");
    twoLevelCache.put(3, "C");

    twoLevelCache.put(4, "D");
    twoLevelCache.put(5, "E");
    twoLevelCache.put(6, "F");

    twoLevelCache.get(1);
    twoLevelCache.get(2);
    twoLevelCache.get(3);
    twoLevelCache.get(5);
    twoLevelCache.get(6);

    //4 will be the LRU key hence it will get replaced
    twoLevelCache.put(7, "G");
    Assertions.assertFalse(twoLevelCache.contains(4));
    Assertions.assertNull(twoLevelCache.get(4));

    Assertions.assertTrue(twoLevelCache.contains(7));
    Assertions.assertEquals("G", twoLevelCache.get(7));
  }

}
