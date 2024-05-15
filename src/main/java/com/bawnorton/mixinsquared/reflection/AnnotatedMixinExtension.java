package com.bawnorton.mixinsquared.reflection;

import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import java.util.Optional;

public final class AnnotatedMixinExtension {
    private final Object reference;

    public AnnotatedMixinExtension(IMixinContext reference) {
        this.reference = reference;
    }

    public static Optional<AnnotatedMixinExtension> tryAs(IMixinContext reference) {
        if(reference.getClass().getName().equals("org.spongepowered.tools.obfuscation.AnnotatedMixin")) {
            return Optional.of(new AnnotatedMixinExtension(reference));
        }
        return Optional.empty();
    }

    public IObfuscationManager getObfuscationManager() {
        return Reflection.accessField(this.reference, "obf", IObfuscationManager.class);
    }

    public ITypeHandleProvider getTypeProvider() {
        return Reflection.accessField(this.reference, "typeProvider", ITypeHandleProvider.class);
    }
}
