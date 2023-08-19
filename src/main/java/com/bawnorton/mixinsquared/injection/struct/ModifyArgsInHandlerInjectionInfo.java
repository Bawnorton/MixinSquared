package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.ModifyArgsInHandler;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyArgsInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@AnnotationType(ModifyArgsInHandler.class)
@HandlerPrefix("argsInHandler")
@SuppressWarnings("unused")
public class ModifyArgsInHandlerInjectionInfo extends MixinSquaredInjectInfo {
    public ModifyArgsInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    public ModifyArgsInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
        super(mixin, method, annotation, atKey);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new ModifyArgsInjector(this);
    }

    @Override
    protected String getDescription() {
        return "Multi-argument modifier method in handler";
    }
}
