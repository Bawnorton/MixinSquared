package com.bawnorton.mixinsquared.reflection;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import java.util.Optional;

public final class MixinInfoExtension {
    private final IMixinInfo reference;

    private final MethodReference<?> stateMethod;

    private MixinInfoExtension(IMixinInfo reference) {
        this.reference = reference;
        this.stateMethod = new MethodReference<>(reference.getClass(), "getState");
    }

    public static Optional<MixinInfoExtension> tryAs(IMixinInfo mixinInfo) {
        if (mixinInfo.getClass().getName().equals("org.spongepowered.asm.mixin.transformer.MixinInfo")) {
            return Optional.of(new MixinInfoExtension(mixinInfo));
        }
        return Optional.empty();
    }

    public Object getState() {
        return stateMethod.invoke(reference);
    }
}
