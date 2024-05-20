package com.bawnorton.mixinsquared.reflection;

import java.lang.reflect.Method;

public final class MethodReference<T> {
    private final Method method;

    public MethodReference(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        Method method;
        try {
            method = clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        method.setAccessible(true);
        this.method = method;
    }

    @SuppressWarnings("unchecked")
    public T invoke(Object instance, Object... args) {
        try {
            return (T) method.invoke(instance, args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
