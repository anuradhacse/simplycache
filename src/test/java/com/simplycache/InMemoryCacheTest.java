package com.simplycache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.simplycache.cache.Cache;
import com.simplycache.evictionpolicy.EvictionPolicy;
import com.simplycache.cache.InMemoryCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author anuradhaj Date: 2/2/21
 */
public class InMemoryCacheTest {

  @Test
  public void testInMemoryLRUCache(){
    Cache<Integer, String> cache = new InMemoryCache<>(3, EvictionPolicy.LRU);

    cache.put(1, "A");
    cache.put(2, "B");
    cache.put(3, "C");

    Assertions.assertEquals(3, cache.size());

    cache.put(4, "D");
    Assertions.assertEquals(3, cache.size());

    //LRU element is removed from the cache - "A"
    Assertions.assertNull(cache.get(1));
    Assertions.assertEquals("B", cache.get(2));
    Assertions.assertEquals("C", cache.get(3));
    Assertions.assertEquals("D", cache.get(4));

    //Scenario 2
    cache = new InMemoryCache<>(3, EvictionPolicy.LRU);
    cache.put(1, "A");
    cache.put(2, "B");
    cache.put(3, "C");

    cache.get(1);
    cache.get(2);

    cache.put(4, "D");

    //since 1, 2 accessed, 3 will be the LRU key
    Assertions.assertNull(cache.get(3));

    Assertions.assertEquals(3, cache.size());
    Assertions.assertEquals("A", cache.get(1));
    Assertions.assertEquals("B", cache.get(2));
    Assertions.assertEquals("D", cache.get(4));

    cache.remove(1);
    Assertions.assertFalse(cache.contains(1));
    Assertions.assertEquals(2, cache.size());

    Assertions.assertFalse(cache.isEmpty());
    cache.clearCache();
    Assertions.assertTrue(cache.isEmpty());
  }

  @Test
  public void testInMemoryLFUCache(){
    Cache<Integer, String> cache = new InMemoryCache<>(3, EvictionPolicy.LFU);

    cache.put(1, "A");
    cache.put(2, "B");
    cache.put(3, "C");

    Assertions.assertEquals(3, cache.size());

    cache.put(4, "D");
    Assertions.assertEquals(3, cache.size());

    //LFU element is removed from the cache since it is the LRU as well - "A"
    Assertions.assertNull(cache.get(1));
    Assertions.assertEquals("B", cache.get(2));
    Assertions.assertEquals("C", cache.get(3));
    Assertions.assertEquals("D", cache.get(4));

    //Scenario 2
    cache = new InMemoryCache<>(3, EvictionPolicy.LRU);
    cache.put(1, "A");
    cache.put(2, "B");
    cache.put(3, "C");

    cache.get(1);
    cache.get(1);

    cache.get(2);
    cache.get(2);

    cache.put(4, "D");

    //since keys 1, 2  more frequently accessed, 3 will be the LFU key
    Assertions.assertNull(cache.get(3));

    Assertions.assertEquals(3, cache.size());
    Assertions.assertEquals("A", cache.get(1));
    Assertions.assertEquals("B", cache.get(2));
    Assertions.assertEquals("D", cache.get(4));

    cache.remove(1);
    Assertions.assertFalse(cache.contains(1));
    Assertions.assertEquals(2, cache.size());

    Assertions.assertFalse(cache.isEmpty());
    cache.clearCache();
    Assertions.assertTrue(cache.isEmpty());
  }

}
