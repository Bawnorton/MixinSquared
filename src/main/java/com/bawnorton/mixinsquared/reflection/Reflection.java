package com.bawnorton.mixinsquared.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class Reflection {
    public static <T> T accessField(Object instance, String fieldName, Class<T> fieldType) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return fieldType.cast(field.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
