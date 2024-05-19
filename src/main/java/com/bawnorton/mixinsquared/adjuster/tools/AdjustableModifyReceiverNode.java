package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;

public class AdjustableModifyReceiverNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableModifyReceiverNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyReceiver.class;
    }
}
