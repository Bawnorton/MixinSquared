package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.InjectInHandler;
import com.google.common.base.Strings;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInjector;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.struct.CallbackInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

import java.util.Set;

@AnnotationType(InjectInHandler.class)
@HandlerPrefix("injectInHandler")
@SuppressWarnings("unused")
public class InjectInHandlerInjectionInfo extends CallbackInjectionInfo implements MixinSquaredInjectInfo {
    public InjectInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override
    public void parseSelectors() {
        MixinSquaredInjectInfo.super.parseSelectors();
    }

    @Override
    public Set<ITargetSelector> getSelectors() {
        return selectors;
    }

    @Override
    public String getAnnotationType() {
        return annotationType;
    }
}
