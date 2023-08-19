package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.ModifyArgInHandler;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyArgInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

@AnnotationType(ModifyArgInHandler.class)
@HandlerPrefix("modifyInHandler")
@SuppressWarnings("unused")
public class ModifyArgInHandlerInjectionInfo extends MixinSquaredInjectInfo {
    public ModifyArgInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    public ModifyArgInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
        super(mixin, method, annotation, atKey);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        int index = Annotations.<Integer>getValue(injectAnnotation, "index", -1);

        return new ModifyArgInjector(this, index);
    }

    @Override
    protected String getDescription() {
        return "Argument modifier method in handler";
    }
}
