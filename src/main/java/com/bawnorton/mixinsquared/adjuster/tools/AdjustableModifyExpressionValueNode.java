package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;

public class AdjustableModifyExpressionValueNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableModifyExpressionValueNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyExpressionValue.class;
    }
}
