package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.ModifyConstantInHandler;
import com.google.common.collect.ImmutableList;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

import java.util.List;

@AnnotationType(ModifyConstantInHandler.class)
@HandlerPrefix("constanttInHandler")
@SuppressWarnings("unused")
public class ModifyConstantInHandlerInjectionInfo extends MixinSquaredInjectInfo {
    private static final String CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');

    public ModifyConstantInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    public ModifyConstantInHandlerInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
        super(mixin, method, annotation, atKey);
    }

    @Override
    protected List<AnnotationNode> readInjectionPoints() {
        List<AnnotationNode> ats = super.readInjectionPoints();
        if (ats.isEmpty()) {
            AnnotationNode c = new AnnotationNode(CONSTANT_ANNOTATION_CLASS);
            c.visit("log", Boolean.TRUE);
            ats = ImmutableList.of(c);
        }
        return ats;
    }

    @Override
    protected void parseInjectionPoints(List<AnnotationNode> ats) {
        Type returnType = Type.getReturnType(this.method.desc);

        for (AnnotationNode at : ats) {
            this.injectionPoints.add(new BeforeConstant(this.getMixin(), at, returnType.getDescriptor()));
        }
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new ModifyConstantInjector(this);
    }

    @Override
    protected String getDescription() {
        return "Constant modifier method in handler";
    }
}
