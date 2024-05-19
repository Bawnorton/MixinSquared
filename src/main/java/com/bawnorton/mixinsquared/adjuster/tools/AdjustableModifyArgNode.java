package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import java.lang.annotation.Annotation;
import java.util.List;
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

    @Override
    public AdjustableModifyArgNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
        return (AdjustableModifyArgNode) super.withSlice(slice);
    }

    @Override
    public AdjustableModifyArgNode withAt(UnaryOperator<AdjustableAtNode> at) {
        return (AdjustableModifyArgNode) super.withAt(at);
    }

    @Override
    public AdjustableModifyArgNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyArgNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyArgNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        return (AdjustableModifyArgNode) super.withTarget(target);
    }

    @Override
    public AdjustableModifyArgNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyArgNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyArgNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyArgNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyArgNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyArgNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyArgNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyArgNode) super.withAllow(allow);
    }

    @Override
    public AdjustableModifyArgNode withConstraints(UnaryOperator<String> constraints) {
        return (AdjustableModifyArgNode) super.withConstraints(constraints);
    }
}
