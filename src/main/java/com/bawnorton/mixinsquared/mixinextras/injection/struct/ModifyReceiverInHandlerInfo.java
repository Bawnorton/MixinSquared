package com.bawnorton.mixinsquared.mixinextras.injection.struct;

import com.bawnorton.mixinsquared.injection.struct.MixinSquaredInjectInfo;
import com.bawnorton.mixinsquared.mixinextras.injection.ModifyReceiverInHandler;
import com.llamalad7.mixinextras.injector.ModifyReceiverInjectionInfo;
import com.llamalad7.mixinextras.injector.ModifyReceiverInjector;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

import java.util.Set;

@AnnotationType(ModifyReceiverInHandler.class)
@HandlerPrefix("modifyRecieverInHandler")
@SuppressWarnings("unused")
public class ModifyReceiverInHandlerInfo extends ModifyReceiverInjectionInfo implements MixinSquaredInjectInfo {
    public ModifyReceiverInHandlerInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
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
