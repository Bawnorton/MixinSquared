package com.bawnorton.mixinsquared.reflection;

import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;

import java.util.List;
import java.util.Map;

public final class ExtensionsAccess {
    @SuppressWarnings("unchecked")
    public static List<IExtension> getExtensions(Extensions instance) {
        return (List<IExtension>) Reflection.accessField(instance, "extensions", List.class);
    }

    @SuppressWarnings("unchecked")
    public static Map<Class<? extends IExtension>, IExtension> getExtensionMap(Extensions instance) {
        return (Map<Class<? extends IExtension>, IExtension>) Reflection.accessField(instance, "extensionMap", Map.class);
    }
}
