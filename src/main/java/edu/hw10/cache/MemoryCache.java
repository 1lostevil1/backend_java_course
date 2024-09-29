package edu.hw10.cache;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache implements Cacheable {
    private final Map<String, String> cache = new HashMap<>();

    @Override
    public String get(String key) {
        return cache.getOrDefault(key, null);
    }

    @Override
    public boolean put(String key, String value) {
        cache.put(key, value);

        return true;
    }
}
