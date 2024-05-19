package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.Slice;
import java.lang.annotation.Annotation;
import java.util.function.UnaryOperator;

public class AdjustableSliceNode extends AdjustableAnnotationNode {
    public AdjustableSliceNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return Slice.class;
    }

    public static AdjustableSliceNode defaultNode() {
        AnnotationNode node = new AnnotationNode(Type.getDescriptor(Slice.class));
        return new AdjustableSliceNode(node);
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
