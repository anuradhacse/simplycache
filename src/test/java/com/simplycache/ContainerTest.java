package com.simplycache;

import com.simplycache.container.Container;
import com.simplycache.container.LFUContainer;
import com.simplycache.container.LRUContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author anuradhaj Date: 2/5/21
 */
public class ContainerTest {

  @Test
  public void testFindKeyToReplace(){

    Container<Integer, String> LRUContainer = new LRUContainer<>(3);
    LRUContainer.put(1, "A");
    LRUContainer.put(2, "B");
    LRUContainer.put(3, "C");


    Assertions.assertEquals(LRUContainer.getKeyToReplace(), 1);

    LRUContainer.get(1);

    Assertions.assertEquals(LRUContainer.getKeyToReplace(), 2);

    Container<Integer, String> LFUContainer = new LFUContainer<>(3);
    LFUContainer.put(1, "A");
    LFUContainer.put(2, "B");
    LFUContainer.put(3, "C");

    Assertions.assertEquals(LFUContainer.getKeyToReplace(), 1);

    LFUContainer.get(1);
    LFUContainer.get(1);
    LFUContainer.get(2);
    LFUContainer.get(2);

    Assertions.assertEquals(LFUContainer.getKeyToReplace(), 3);

  }
}
