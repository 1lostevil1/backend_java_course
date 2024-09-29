package edu.hw10.Task2;

import edu.hw10.annotations.Cache;
import edu.hw10.cache.Cacheable;
import edu.hw10.cache.FileCache;
import edu.hw10.cache.MemoryCache;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CacheProxyHandler implements InvocationHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Object target;
    private final FileCache fileCache;
    private final MemoryCache memoryCache;

    @SuppressWarnings("checkstyle:HiddenField")
    public CacheProxyHandler(Object target) {
        FileCache fileCache;
        try {
            fileCache = new FileCache(target.getClass().getName());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());

            fileCache = null;
        }

        this.target = target;
        this.fileCache = fileCache;
        this.memoryCache = new MemoryCache();
    }

    @Override
    public Object invoke(
        Object proxy,
        Method method,
        Object[] args
    ) throws InvocationTargetException, IllegalAccessException {
        Method valueOf;

        try {
            valueOf = method.getReturnType().getMethod("valueOf", String.class);

            if (!Modifier.isStatic(valueOf.getModifiers())) {
                throw new NoSuchMethodException();
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                "Возвращаемый тип должен поддерживать статический метод valueOf(String)"
            );
        }

        try {
            // Проверка наличия аннотации Cache
            if (method.isAnnotationPresent(Cache.class)) {
                Cache annotation = method.getAnnotation(Cache.class);
                String cacheKey = target.getClass().getName() + "_" + method.getName() + Arrays.toString(args);

                String fromCache = getFromCache(annotation.persist(), cacheKey);

                if (fromCache != null) {
                    return valueOf.invoke(null, fromCache);
                }

                // Если нет в кэше, выполнение метода и сохранение в кэш
                Object result = method.invoke(target, args);
                putToCache(annotation.persist(), cacheKey, result.toString());

                return result;
            } else {
                // Если метод не помечен аннотацией Cache, просто выполнение метода
                return method.invoke(target, args);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.error(e.getMessage());

            throw e;
        }

    }

    private String getFromCache(boolean persist, String key) {
        Cacheable cache = persist ? fileCache : memoryCache;

        if (cache == null) {
            return null;
        }

        return cache.get(key);
    }

    private void putToCache(boolean persist, String key, String value) {
        Cacheable cache = persist ? fileCache : memoryCache;

        if (cache == null) {
            return;
        }

        cache.put(key, value);
    }
}
