package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;

public class AdjustableWrapWithConditionNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableWrapWithConditionNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return WrapWithCondition.class;
    }
}
