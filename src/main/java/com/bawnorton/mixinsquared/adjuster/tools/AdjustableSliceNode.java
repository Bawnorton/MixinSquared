package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.Slice;
import java.lang.annotation.Annotation;
import java.util.function.UnaryOperator;

public class AdjustableSliceNode extends AdjustableAnnotationNode {
    public static final AdjustableSliceNode DEFAULT;

    public AdjustableSliceNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return Slice.class;
    }

    static {
        AnnotationNode node = new AnnotationNode(Type.getDescriptor(Slice.class));
        DEFAULT = new AdjustableSliceNode(node);
    }

    public static AdjustableSliceNode[] fromArray(AnnotationNode[] nodes) {
        AdjustableSliceNode[] slices = new AdjustableSliceNode[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            slices[i] = new AdjustableSliceNode(nodes[i]);
        }
        return slices;
    }

    public String getId() {
        return this.<String>get("id").orElse("");
    }

    public void setId(String id) {
        this.set("id", id);
    }

    public AdjustableSliceNode withId(UnaryOperator<String> id) {
        this.setId(id.apply(this.getId()));
        return this;
    }

    public AdjustableAtNode getFrom() {
        return this.<AnnotationNode>get("from")
                .map(AdjustableAtNode::new)
                .orElse(AdjustableAtNode.InjectionPoint.HEAD.toNode());
    }

    public void setFrom(AdjustableAtNode from) {
        this.set("from", from);
    }

    public AdjustableSliceNode withFrom(UnaryOperator<AdjustableAtNode> from) {
        this.setFrom(from.apply(this.getFrom()));
        return this;
    }

    public AdjustableAtNode getTo() {
        return this.<AnnotationNode>get("to")
                .map(AdjustableAtNode::new)
                .orElse(AdjustableAtNode.InjectionPoint.TAIL.toNode());
    }

    public void setTo(AdjustableAtNode to) {
        this.set("to", to);
    }

    public AdjustableSliceNode withTo(UnaryOperator<AdjustableAtNode> to) {
        this.setTo(to.apply(this.getTo()));
        return this;
    }
}
