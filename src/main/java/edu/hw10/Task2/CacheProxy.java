package edu.hw10.Task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class CacheProxy {
    private CacheProxy() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(Object target, Class<T> interfaceClass) {
        ClassLoader classLoader = interfaceClass.getClassLoader();
        Class<?>[] interfaces = {interfaceClass};
        InvocationHandler handler = new CacheProxyHandler(target);

        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}
