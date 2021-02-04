package com.simplycache.impl;

import static java.lang.String.format;

import com.simplycache.evictionpolicy.EvictionPolicy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author anuradhaj Date: 2/3/21
 * Cache which use file to store values
 * This class in not Thread safe
 */
public class FileSystemCache<K, V extends Serializable> extends AbstractCache<K, V, String>{

    private static final Logger LOGGER = Logger.getLogger(FileSystemCache.class.getName());
    private final Path tempFolder;

    public FileSystemCache(int size, EvictionPolicy evictionPolicy) {
      super(size, evictionPolicy);
      try {
        this.tempFolder = Files.createTempDirectory("simplycache");
        this.tempFolder.toFile().deleteOnExit();
      } catch (IOException e) {
        throw new RuntimeException(format("Failed to create directory. %s", e.getMessage()));
      }
    }

    @Override
    public void put(K key, V val) {
      File tmpFile = null;
      try {
        tmpFile = Files.createTempFile(tempFolder, "file_", ".cache").toFile();
        LOGGER.log(Level.INFO, "Created file for key  - {0}",  tmpFile.getAbsolutePath());
      } catch (IOException e) {
        throw new RuntimeException(format("Failed to create file. %s", e.getMessage()));
      }

      if (tmpFile != null) {
        try (OutputStream fileOutputStream = new FileOutputStream(tmpFile);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
          outputStream.writeObject(val);
          outputStream.flush();
          cache.put(key, tmpFile.getName());
          LOGGER.log(Level.INFO, "Put an object with key {0} into file system cache", key);
        } catch (IOException e) {
          throw new RuntimeException(format("Failed to write an object to a file '%s': %s",
              tmpFile.getName(), e.getMessage()));
        }
      }
    }

    @Override
    public V get(K key) {
      if(cache.containsKey(key)){
        String fileName = cache.get(key);
        try (InputStream fileInputStream = new FileInputStream(new File(tempFolder + File.separator + fileName));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
          V value = (V) objectInputStream.readObject();
          LOGGER.log(Level.INFO, "Get an object with key {0} from file system cache", key);
          return value;
        } catch (ClassNotFoundException | IOException e) {
          throw new RuntimeException("Error while getting from cache");
        }

      }
      return null;
    }
}
