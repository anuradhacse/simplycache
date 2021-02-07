package com.simplycache;

import static org.junit.jupiter.api.Assertions.fail;

import com.simplycache.cache.Cache;
import com.simplycache.cache.CacheException;
import com.simplycache.cache.TwoLevelCache;
import com.simplycache.common.ErrorCodes;
import com.simplycache.evictionpolicy.EvictionPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author anuradhaj Date: 2/6/21
 */
public class TwoLevelCacheTest {

  @Test
  public void testTwoLevelLRUCacheBasicOperations(){

    Cache<Integer, String> twoLevelCache = new TwoLevelCache<>(3,3, EvictionPolicy.LRU);

    twoLevelCache.put(1, "A");
    twoLevelCache.put(2, "B");
    twoLevelCache.put(3, "C");

    twoLevelCache.put(4, "D");
    twoLevelCache.put(5, "E");
    twoLevelCache.put(6, "F");

    Assertions.assertEquals(6, twoLevelCache.size());
    Assertions.assertTrue(twoLevelCache.contains(1));
    Assertions.assertEquals("A", twoLevelCache.get(1));
    Assertions.assertTrue(twoLevelCache.contains(2));
    Assertions.assertEquals("B", twoLevelCache.get(2));
    Assertions.assertTrue(twoLevelCache.contains(3));
    Assertions.assertEquals("C", twoLevelCache.get(3));
    Assertions.assertTrue(twoLevelCache.contains(4));
    Assertions.assertEquals("D", twoLevelCache.get(4));
    Assertions.assertTrue(twoLevelCache.contains(5));
    Assertions.assertEquals("E", twoLevelCache.get(5));
    Assertions.assertTrue(twoLevelCache.contains(6));
    Assertions.assertEquals("F", twoLevelCache.get(6));

    twoLevelCache.remove(1);
    Assertions.assertNull(twoLevelCache.get(1));
    Assertions.assertFalse(twoLevelCache.contains(1));
    Assertions.assertEquals(5, twoLevelCache.size());

    twoLevelCache.remove(5);
    Assertions.assertNull(twoLevelCache.get(5));
    Assertions.assertFalse(twoLevelCache.contains(5));
    Assertions.assertEquals(4, twoLevelCache.size());

    twoLevelCache.clearCache();
    Assertions.assertTrue(twoLevelCache.isEmpty());
  }

  @Test
  public void testTwoLevelLFUCacheBasicOperations(){

    Cache<Integer, String> twoLevelCache = new TwoLevelCache<>(3,3, EvictionPolicy.LFU);

    twoLevelCache.put(1, "A");
    twoLevelCache.put(2, "B");
    twoLevelCache.put(3, "C");

    twoLevelCache.put(4, "D");
    twoLevelCache.put(5, "E");
    twoLevelCache.put(6, "F");

    Assertions.assertEquals(6, twoLevelCache.size());
    Assertions.assertTrue(twoLevelCache.contains(1));
    Assertions.assertEquals("A", twoLevelCache.get(1));
    Assertions.assertTrue(twoLevelCache.contains(2));
    Assertions.assertEquals("B", twoLevelCache.get(2));
    Assertions.assertTrue(twoLevelCache.contains(3));
    Assertions.assertEquals("C", twoLevelCache.get(3));
    Assertions.assertTrue(twoLevelCache.contains(4));
    Assertions.assertEquals("D", twoLevelCache.get(4));
    Assertions.assertTrue(twoLevelCache.contains(5));
    Assertions.assertEquals("E", twoLevelCache.get(5));
    Assertions.assertTrue(twoLevelCache.contains(6));
    Assertions.assertEquals("F", twoLevelCache.get(6));

    twoLevelCache.remove(1);
    Assertions.assertNull(twoLevelCache.get(1));
    Assertions.assertFalse(twoLevelCache.contains(1));
    Assertions.assertEquals(5, twoLevelCache.size());

    twoLevelCache.clearCache();
    Assertions.assertTrue(twoLevelCache.isEmpty());
  }

  @Test
  public void testTwoLevelCacheLRUKeyReplacement(){

    Cache<Integer, String> twoLevelCache = new TwoLevelCache<>(3,3, EvictionPolicy.LRU);

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

    //Now cache is full, 4 will be the LRU key hence it will get replaced
    twoLevelCache.put(7, "G");
    Assertions.assertFalse(twoLevelCache.contains(4));
    Assertions.assertNull(twoLevelCache.get(4));

    Assertions.assertTrue(twoLevelCache.contains(7));
    Assertions.assertEquals("G", twoLevelCache.get(7));
  }

  @Test
  public void testTwoLevelCacheLFUKeyReplacement(){

    Cache<Integer, String> twoLevelCache = new TwoLevelCache<>(3,3, EvictionPolicy.LFU);

    twoLevelCache.put(1, "A");
    twoLevelCache.put(2, "B");
    twoLevelCache.put(3, "C");

    twoLevelCache.put(4, "D");
    twoLevelCache.put(5, "E");
    twoLevelCache.put(6, "F");

    twoLevelCache.get(1);
    twoLevelCache.get(1);

    twoLevelCache.get(2);
    twoLevelCache.get(2);
    twoLevelCache.get(2);

    twoLevelCache.get(3);

    twoLevelCache.get(4);
    twoLevelCache.get(4);

    twoLevelCache.get(5);
    twoLevelCache.get(5);
    twoLevelCache.get(5);

    twoLevelCache.get(6);
    twoLevelCache.get(6);

    //Now cache is full, 3 will be the LRU key hence it will get replaced
    twoLevelCache.put(7, "G");
    Assertions.assertFalse(twoLevelCache.contains(3));
    Assertions.assertNull(twoLevelCache.get(3));

    Assertions.assertTrue(twoLevelCache.contains(7));
    Assertions.assertEquals("G", twoLevelCache.get(7));
  }

  @Test
  public void testInvalidInitialSize(){
    try{
      TwoLevelCache<Integer, Integer> inMemoryCache = new TwoLevelCache<>(0,0, EvictionPolicy.LFU);
      fail("Exception should have thrown");
    } catch (Exception ex){
      Assertions.assertNotNull(ex);
      Assertions.assertTrue(ex instanceof CacheException);
      Assertions.assertEquals("initial size should be greater than 0", ((CacheException)ex).getMessage());
      Assertions.assertEquals(ErrorCodes.INITIAL_CACHE_SIZE_ERROR, ((CacheException)ex).getErrorCode());
    }
  }

}
