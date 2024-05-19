package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyArgsNode extends AdjustableInjectorNode {
    public AdjustableModifyArgsNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyArgs.class;
    }

    public AdjustableSliceNode getSlice() {
        return this.<AnnotationNode>get("slice")
                .map(AdjustableSliceNode::new)
                .orElse(AdjustableSliceNode.DEFAULT);
    }

    public void setSlice(AdjustableSliceNode slice) {
        this.set("slice", slice);
    }

    public AdjustableModifyArgsNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
        this.setSlice(slice.apply(this.getSlice()));
        return this;
    }

    public AdjustableAtNode getAt() {
        return this.<AnnotationNode>get("at")
                .map(AdjustableAtNode::new)
                .orElse(null);
    }

    public void setAt(AdjustableAtNode at) {
        if (at == null) throw new IllegalArgumentException("At cannot be null");
        this.set("at", at);
    }

    public AdjustableModifyArgsNode withAt(UnaryOperator<AdjustableAtNode> at) {
        this.setAt(at.apply(this.getAt()));
        return this;
    }

    @Override
    public AdjustableModifyArgsNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyArgsNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyArgsNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        return (AdjustableModifyArgsNode) super.withTarget(target);
    }

    @Override
    public AdjustableModifyArgsNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyArgsNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyArgsNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyArgsNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyArgsNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyArgsNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyArgsNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyArgsNode) super.withAllow(allow);
    }

    @Override
    public AdjustableModifyArgsNode withConstraints(UnaryOperator<String> constraints) {
        return (AdjustableModifyArgsNode) super.withConstraints(constraints);
    }
}
