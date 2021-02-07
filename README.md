# simplycache

**simplycache** is a caching library that supports InMemory caching, File System Caching and
Two Level Caching.   
Supported Eviction policies - LRU, LFU

##Usage

### 1. InMemory Cache
 > Cache<Integer, String> cache = new InMemoryCache<>(3, EvictionPolicy.LRU);  
   cache.put(1,"A")  
   cache.get(1) -> A

### 2. FileSystem Cache
> Cache<Integer, String> cache = new FileSystemCache<>(3, EvictionPolicy.LFU);  
   cache.put(1,"A")  
   cache.get(1) -> A

### 3. TwoLevel Cache
> Cache<Integer, String> cache = new TwoLevelCache<>(3,3, EvictionPolicy.LRU);
    cache.put(1,"A")  
    cache.get(1) -> A
