package com.bawnorton.mixinsquared.canceller;

import com.bawnorton.mixinsquared.reflection.ExtensionsAccess;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;

public abstract class ExtensionRegistrar {
    public static void register(IExtension extension) {
        IMixinTransformer transformer = (IMixinTransformer) MixinEnvironment.getDefaultEnvironment().getActiveTransformer();
        Extensions extensions = (Extensions) transformer.getExtensions();
        ExtensionsAccess.getExtensions(extensions).add(0, extension);
        ExtensionsAccess.getExtensionMap(extensions).put(extension.getClass(), extension);
    }
}
