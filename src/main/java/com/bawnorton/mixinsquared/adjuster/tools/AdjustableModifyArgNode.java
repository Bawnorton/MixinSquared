package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import java.lang.annotation.Annotation;
import java.util.function.UnaryOperator;

public class AdjustableModifyArgNode extends AdjustableModifyArgsNode {
    public AdjustableModifyArgNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyArg.class;
    }

    public int getIndex() {
        return this.<Integer>get("index").orElse(-1);
    }

    public void setIndex(int index) {
        this.set("index", index);
    }

    public AdjustableModifyArgNode withIndex(UnaryOperator<Integer> index) {
        this.setIndex(index.apply(this.getIndex()));
        return this;
    }
}
