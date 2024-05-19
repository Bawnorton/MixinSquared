package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableWrapWithConditionNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableWrapWithConditionNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return WrapWithCondition.class;
    }

    @Override
    public AdjustableWrapWithConditionNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableWrapWithConditionNode) super.withMethod(method);
    }

    @Override
    public AdjustableWrapWithConditionNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableWrapWithConditionNode) super.withAt(at);
    }

    @Override
    public AdjustableWrapWithConditionNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableWrapWithConditionNode) super.withSlice(slice);
    }

    @Override
    public AdjustableWrapWithConditionNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableWrapWithConditionNode) super.withRemap(remap);
    }

    @Override
    public AdjustableWrapWithConditionNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableWrapWithConditionNode) super.withRequire(require);
    }

    @Override
    public AdjustableWrapWithConditionNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableWrapWithConditionNode) super.withExpect(expect);
    }

    @Override
    public AdjustableWrapWithConditionNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableWrapWithConditionNode) super.withAllow(allow);
    }
}
