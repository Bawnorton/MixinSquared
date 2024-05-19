package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyReturnValueNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableModifyReturnValueNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyReturnValue.class;
    }

    @Override
    public AdjustableModifyReturnValueNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyReturnValueNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyReturnValueNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableModifyReturnValueNode) super.withAt(at);
    }

    @Override
    public AdjustableModifyReturnValueNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableModifyReturnValueNode) super.withSlice(slice);
    }

    @Override
    public AdjustableModifyReturnValueNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyReturnValueNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyReturnValueNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyReturnValueNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyReturnValueNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyReturnValueNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyReturnValueNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyReturnValueNode) super.withAllow(allow);
    }
}
