package com.simplycache.cache;

/**
 * @author anuradhaj Date: 2/6/21
 * Custom exception class to represent unchecked cache exceptions
 */
public class CacheException extends RuntimeException {

  private final String errorCode;

    public CacheException(String message, String errorCode) {
      super(message);
      this.errorCode = errorCode;
    }

    public CacheException(String message,String errorCode, Throwable throwable) {
      super(message, throwable);
      this.errorCode = errorCode;
    }

    public String getErrorCode() {
      return errorCode;
    }
}
