package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableRedirectNode extends AdjustableInjectorNode {
    public AdjustableRedirectNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return Redirect.class;
    }

    public AdjustableSliceNode getSlice() {
        return this.<AnnotationNode>get("slice")
                .map(AdjustableSliceNode::new)
                .orElse(AdjustableSliceNode.DEFAULT);
    }

    public void setSlice(AdjustableSliceNode slice) {
        this.set("slice", slice);
    }

    public AdjustableRedirectNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
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

    public AdjustableRedirectNode withAt(UnaryOperator<AdjustableAtNode> at) {
        this.setAt(at.apply(this.getAt()));
        return this;
    }

    @Override
    public AdjustableRedirectNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableRedirectNode) super.withMethod(method);
    }

    @Override
    public AdjustableRedirectNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        return (AdjustableRedirectNode) super.withTarget(target);
    }

    @Override
    public AdjustableRedirectNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableRedirectNode) super.withRemap(remap);
    }

    @Override
    public AdjustableRedirectNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableRedirectNode) super.withRequire(require);
    }

    @Override
    public AdjustableRedirectNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableRedirectNode) super.withExpect(expect);
    }

    @Override
    public AdjustableRedirectNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableRedirectNode) super.withAllow(allow);
    }

    @Override
    public AdjustableRedirectNode withConstraints(UnaryOperator<String> constraints) {
        return (AdjustableRedirectNode) super.withConstraints(constraints);
    }
}
