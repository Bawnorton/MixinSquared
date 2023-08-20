package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.RedirectInHandler;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.injection.struct.RedirectInjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

import java.util.Set;

@AnnotationType(RedirectInHandler.class)
@HandlerPrefix("redirectInHandler")
@SuppressWarnings("unused")
public class RedirectInHandlerInjectionInfo extends RedirectInjectionInfo implements MixinSquaredInjectInfo {
    public RedirectInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
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
