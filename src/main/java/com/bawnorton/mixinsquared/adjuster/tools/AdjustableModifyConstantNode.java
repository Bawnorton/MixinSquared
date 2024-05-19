package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyConstantNode extends AdjustableInjectorNode {
    public AdjustableModifyConstantNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyConstant.class;
    }

    public List<AdjustableSliceNode> getSlice() {
        return this.<List<AnnotationNode>>get("slice")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableSliceNode::new))
                .orElse(new ArrayList<>());
    }

    public void setSlice(List<AdjustableSliceNode> slice) {
        this.set("slice", slice);
    }

    public AdjustableModifyConstantNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        this.setSlice(slice.apply(this.getSlice()));
        return this;
    }

    public List<AdjustableConstantNode> getConstant() {
        return this.<List<AnnotationNode>>get("constant")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableConstantNode::new))
                .orElse(new ArrayList<>());
    }

    public void setConstant(List<AdjustableConstantNode> constant) {
        this.set("constant", constant);
    }

    public AdjustableModifyConstantNode withConstant(UnaryOperator<List<AdjustableConstantNode>> constant) {
        this.setConstant(constant.apply(this.getConstant()));
        return this;
    }

    @Override
    public AdjustableModifyConstantNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyConstantNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyConstantNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        return (AdjustableModifyConstantNode) super.withTarget(target);
    }

    @Override
    public AdjustableModifyConstantNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyConstantNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyConstantNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyConstantNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyConstantNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyConstantNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyConstantNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyConstantNode) super.withAllow(allow);
    }

    @Override
    public AdjustableModifyConstantNode withConstraints(UnaryOperator<String> constraints) {
        return (AdjustableModifyConstantNode) super.withConstraints(constraints);
    }
}
