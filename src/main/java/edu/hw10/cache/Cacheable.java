package edu.hw10.cache;

public interface Cacheable {
    String get(String key);

    boolean put(String key, String value);
}
