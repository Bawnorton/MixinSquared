package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.RedirectInHandler;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.RedirectInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@AnnotationType(RedirectInHandler.class)
@HandlerPrefix("redirectInHandler")
@SuppressWarnings("unused")
public class RedirectInHandlerInjectionInfo extends MixinSquaredInjectInfo {
    public RedirectInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    public RedirectInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
        super(mixin, method, annotation, atKey);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new RedirectInjector(this);
    }

    @Override
    protected String getDescription() {
        return "Redirector in handler";
    }
}
