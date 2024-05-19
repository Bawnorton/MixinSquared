package com.bawnorton.mixinsquared.adjuster.tools;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyReceiverNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableModifyReceiverNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyReceiver.class;
    }

    public static AdjustableModifyReceiverNode defaultNode(List<String> methods, List<AdjustableAtNode> atNodes) {
        AnnotationNode node = new AnnotationNode(Type.getDescriptor(ModifyReceiver.class));
        AdjustableModifyReceiverNode defaultNode = new AdjustableModifyReceiverNode(node);
        defaultNode.setMethod(methods);
        defaultNode.setAt(atNodes);
        return defaultNode;
    }

    @Override
    public AdjustableModifyReceiverNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyReceiverNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyReceiverNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableModifyReceiverNode) super.withAt(at);
    }

    @Override
    public AdjustableModifyReceiverNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableModifyReceiverNode) super.withSlice(slice);
    }

    @Override
    public AdjustableModifyReceiverNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyReceiverNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyReceiverNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyReceiverNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyReceiverNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyReceiverNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyReceiverNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyReceiverNode) super.withAllow(allow);
    }
}
