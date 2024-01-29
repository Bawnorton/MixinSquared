package com.bawnorton.mixinsquared.canceller;

import com.bawnorton.mixinsquared.reflection.TargetClassContextAccess;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.service.MixinService;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public final class ExtensionCancelApplication implements IExtension {
    private static final ILogger LOGGER = MixinService.getService().getLogger("mixinsquared");

    @Override
    public boolean checkActive(MixinEnvironment environment) {
        return true;
    }

    @Override
    public void preApply(ITargetClassContext context) {
        SortedSet<IMixinInfo> mixins = TargetClassContextAccess.getMixins(context);
        mixins.removeIf(mixin -> {
            List<String> targetClasses = mixin.getTargetClasses().stream().map(s -> s.replaceAll("/", ".")).collect(Collectors.toList());
            boolean shouldCancel = MixinCancellerRegistrar.shouldCancel(targetClasses, mixin.getClassName());
            if (shouldCancel) {
                LOGGER.debug("Cancelling mixin {}", mixin.getClassName());
            }
            return shouldCancel;
        });
    }

    @Override
    public void postApply(ITargetClassContext context) {
    }

    @Override
    public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {
    }
}
