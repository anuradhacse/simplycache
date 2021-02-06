package com.simplycache.cache;

import static java.lang.String.format;

import com.simplycache.common.ErrorCodes;
import com.simplycache.evictionpolicy.EvictionPolicy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author anuradhaj Date: 2/3/21
 * Cache which use file to store values
 * This class in not Thread safe
 */
public class FileSystemCache<K, V extends Serializable> extends AbstractCache<K, V, String>{

    private static final Logger LOGGER = LogManager.getLogger(FileSystemCache.class);

    private final Path tempFolder;

    public FileSystemCache(int size, EvictionPolicy evictionPolicy) {
      super(size, evictionPolicy);
      try {
        this.tempFolder = Files.createTempDirectory("simplycache");
        this.tempFolder.toFile().deleteOnExit();
      } catch (IOException e) {
        throw new CacheException("Failed to create directory", ErrorCodes.ERROR_CREATING_DIRECTORY, e);
      }
    }

    @Override
    public void put(K key, V val) {
      File tmpFile = null;
      try {
        tmpFile = Files.createTempFile(tempFolder, "file_", ".cache").toFile();
      } catch (IOException e) {
        throw new CacheException("Failed to create file", ErrorCodes.ERROR_CREATING_FILE, e);
      }

      if (tmpFile != null) {
        try (OutputStream fileOutputStream = new FileOutputStream(tmpFile);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
          outputStream.writeObject(val);
          outputStream.flush();
          container.put(key, tmpFile.getName());
          LOGGER.info("Putting an object with key {} into file system cache", key);
        } catch (IOException e) {
          throw new CacheException("Failed write data to file", ErrorCodes.ERROR_WRITING_TO_FILE, e);
        }
      }
    }

    @Override
    public V get(K key) {
      if(container.containsKey(key)){
        String fileName = container.get(key);
        try (InputStream fileInputStream = new FileInputStream(new File(tempFolder + File.separator + fileName));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
          V value = (V) objectInputStream.readObject();
          LOGGER.info("Getting an object with key {} from file system cache", key);
          return value;
        } catch (ClassNotFoundException | IOException e) {
          throw new CacheException("Failed read data from file", ErrorCodes.ERROR_READING_FROM_FILE, e);
        }
      }
      return null;
    }
}
