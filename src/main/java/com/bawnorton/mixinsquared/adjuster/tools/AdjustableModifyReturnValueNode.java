package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;

public class AdjustableModifyReturnValueNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableModifyReturnValueNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyReturnValue.class;
    }
}
