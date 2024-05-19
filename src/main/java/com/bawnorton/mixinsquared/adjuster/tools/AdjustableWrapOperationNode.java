package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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

    public List<AdjustableConstantNode> getConstants() {
        return this.<List<AnnotationNode>>get("constants")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableConstantNode::new))
                .orElse(new ArrayList<>());
    }

    public void setConstants(List<AdjustableConstantNode> constants) {
        this.set("constants", constants);
    }

    public AdjustableWrapOperationNode withConstants(UnaryOperator<List<AdjustableConstantNode>> constants) {
        this.setConstants(constants.apply(this.getConstants()));
        return this;
    }
}
