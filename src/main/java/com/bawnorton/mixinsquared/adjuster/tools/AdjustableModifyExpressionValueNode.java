package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyExpressionValueNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableModifyExpressionValueNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyExpressionValue.class;
    }

    public static AdjustableModifyExpressionValueNode defaultNode(List<String> methods, List<AdjustableAtNode> atNodes) {
        AnnotationNode node = new AnnotationNode(Type.getDescriptor(ModifyExpressionValue.class));
        AdjustableModifyExpressionValueNode defaultNode = new AdjustableModifyExpressionValueNode(node);
        defaultNode.setMethod(methods);
        defaultNode.setAt(atNodes);
        return defaultNode;
    }

    @Override
    public AdjustableModifyExpressionValueNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyExpressionValueNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyExpressionValueNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableModifyExpressionValueNode) super.withAt(at);
    }

    @Override
    public AdjustableModifyExpressionValueNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableModifyExpressionValueNode) super.withSlice(slice);
    }

    @Override
    public AdjustableModifyExpressionValueNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyExpressionValueNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyExpressionValueNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyExpressionValueNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyExpressionValueNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyExpressionValueNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyExpressionValueNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyExpressionValueNode) super.withAllow(allow);
    }
}
