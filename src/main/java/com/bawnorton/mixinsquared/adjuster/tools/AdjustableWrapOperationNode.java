package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableWrapOperationNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableWrapOperationNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return WrapOperation.class;
    }

    public static AdjustableWrapOperationNode defaultNode(List<String> methods) {
        AnnotationNode node = new AnnotationNode(Type.getDescriptor(WrapOperation.class));
        AdjustableWrapOperationNode defaultNode = new AdjustableWrapOperationNode(node);
        defaultNode.setMethod(methods);
        return defaultNode;
    }

    @Override
    public List<AdjustableAtNode> getAt() {
        List<AdjustableAtNode> ats = super.getAt();
        if (ats == null) return new ArrayList<>();
        return ats;
    }

    @Override
    public void setAt(List<AdjustableAtNode> at) {
        this.set("at", at);
    }

    public List<AdjustableConstantNode> getConstant() {
        return this.<List<AnnotationNode>>get("constants")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableConstantNode::new))
                .orElse(new ArrayList<>());
    }

    public void setConstant(List<AdjustableConstantNode> constants) {
        this.set("constant", constants);
    }

    public AdjustableWrapOperationNode withConstant(UnaryOperator<List<AdjustableConstantNode>> constants) {
        this.setConstant(constants.apply(this.getConstant()));
        return this;
    }

    @Override
    public AdjustableWrapOperationNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableWrapOperationNode) super.withMethod(method);
    }

    @Override
    public AdjustableWrapOperationNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableWrapOperationNode) super.withAt(at);
    }

    @Override
    public AdjustableWrapOperationNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableWrapOperationNode) super.withSlice(slice);
    }

    @Override
    public AdjustableWrapOperationNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableWrapOperationNode) super.withRemap(remap);
    }

    @Override
    public AdjustableWrapOperationNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableWrapOperationNode) super.withRequire(require);
    }

    @Override
    public AdjustableWrapOperationNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableWrapOperationNode) super.withExpect(expect);
    }

    @Override
    public AdjustableWrapOperationNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableWrapOperationNode) super.withAllow(allow);
    }
}
