package com.bawnorton.mixinsquared.canceller;

import com.bawnorton.mixinsquared.reflection.TargetClassContextAccess;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;

import java.util.SortedSet;

public final class ExtensionCancelApplication implements IExtension {
    @Override
    public boolean checkActive(MixinEnvironment environment) {
        return true;
    }

    @Override
    public void preApply(ITargetClassContext context) {
        SortedSet<IMixinInfo> mixins = TargetClassContextAccess.getMixins(context);
        mixins.removeIf(mixin -> MixinCancellerRegistrar.shouldCancel(mixin.getTargetClasses(), mixin.getClassName()));
    }

    @Override
    public void postApply(ITargetClassContext context) {
    }

    @Override
    public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {
    }
}
