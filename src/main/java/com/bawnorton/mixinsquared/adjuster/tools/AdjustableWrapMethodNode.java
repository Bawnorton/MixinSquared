package com.bawnorton.mixinsquared.adjuster.tools;

import com.bawnorton.mixinsquared.adjuster.tools.type.MatchCountAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.MethodListAnnotationNode;
import org.objectweb.asm.tree.AnnotationNode;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableWrapMethodNode extends RemapperHolderAnnotationNode implements MethodListAnnotationNode, MatchCountAnnotationNode {
    public AdjustableWrapMethodNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableWrapMethodNode defaultNode(List<String> method) {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.WRAP_METHOD.desc());
        AdjustableWrapMethodNode defaultNode = new AdjustableWrapMethodNode(node);
        defaultNode.setMethod(method);
        return defaultNode;
    }

    @Override
    public AdjustableWrapMethodNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableWrapMethodNode) MethodListAnnotationNode.super.withMethod(method);
    }

    @Override
    public AdjustableWrapMethodNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableWrapMethodNode) super.withRemap(remap);
    }

    @Override
    public AdjustableWrapMethodNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableWrapMethodNode) MatchCountAnnotationNode.super.withRequire(require);
    }

    @Override
    public AdjustableWrapMethodNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableWrapMethodNode) MatchCountAnnotationNode.super.withExpect(expect);
    }

    @Override
    public AdjustableWrapMethodNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableWrapMethodNode) MatchCountAnnotationNode.super.withAllow(allow);
    }

    @Override
    public void applyRefmap(UnaryOperator<String> refmapApplicator) {
        MethodListAnnotationNode.super.applyRefmap(refmapApplicator);
    }
}
