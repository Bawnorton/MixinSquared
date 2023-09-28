package com.bawnorton.mixinsquared.reflection;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;

import java.util.SortedSet;

public final class TargetClassContextAccess {
    @SuppressWarnings("unchecked")
    public static SortedSet<IMixinInfo> getMixins(ITargetClassContext context) {
        return Reflection.accessField(context, "mixins", SortedSet.class);
    }
}
