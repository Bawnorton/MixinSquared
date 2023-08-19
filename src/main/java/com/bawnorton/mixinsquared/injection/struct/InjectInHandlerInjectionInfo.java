package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.InjectInHandler;
import com.google.common.base.Strings;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInjector;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

@AnnotationType(InjectInHandler.class)
@HandlerPrefix("injectInHandler")
@SuppressWarnings("unused")
public class InjectInHandlerInjectionInfo extends MixinSquaredInjectInfo {
    public InjectInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    public InjectInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
        super(mixin, method, annotation, atKey);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        boolean cancellable = Annotations.<Boolean>getValue(injectAnnotation, "cancellable", Boolean.FALSE);
        LocalCapture locals = Annotations.getValue(injectAnnotation, "locals", LocalCapture.class, LocalCapture.NO_CAPTURE);
        String identifier = Annotations.getValue(injectAnnotation, "id", "");

        return new CallbackInjector(this, cancellable, locals, identifier);
    }

    @Override
    protected String getDescription() {
        return "Callback method in handler";
    }

    @Override
    public String getSliceId(String id) {
        return Strings.nullToEmpty(id);
    }
}
