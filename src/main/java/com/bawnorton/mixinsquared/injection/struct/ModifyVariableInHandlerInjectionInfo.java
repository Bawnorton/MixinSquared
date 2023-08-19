package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.ModifyVariableInHandler;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@AnnotationType(ModifyVariableInHandler.class)
@HandlerPrefix("localvarInHandler")
@SuppressWarnings("unused")
public class ModifyVariableInHandlerInjectionInfo extends MixinSquaredInjectInfo {
    public ModifyVariableInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    public ModifyVariableInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
        super(mixin, method, annotation, atKey);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new ModifyVariableInjector(this, LocalVariableDiscriminator.parse(injectAnnotation));
    }

    @Override
    protected String getDescription() {
        return "Variable modifier method in handler";
    }
}
