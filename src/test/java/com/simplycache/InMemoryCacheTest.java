package com.simplycache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.simplycache.impl.InMemoryCache;
import org.junit.jupiter.api.Test;

/**
 * @author anuradhaj Date: 2/2/21
 */
public class InMemoryCacheTest {

  private final InMemoryCache inMemoryCache = new InMemoryCache();

  @Test
  public void testCacheGet(){
    assertEquals("value", inMemoryCache.getValue("key"));
  }

  @Test
  public void testCachePut(){
    assertEquals("key-value", inMemoryCache.putValue("key","value"));
  }

}
