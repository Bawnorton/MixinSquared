package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.tree.AnnotationNode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public abstract class AdjustableMixinExtrasInjectorNode extends AdjustableAnnotationNode {
    protected AdjustableMixinExtrasInjectorNode(AnnotationNode node) {
        super(node);
    }

    public String[] getMethod() {
        return this.<String[]>get("method").orElse(null);
    }

    public void setMethod(String... method) {
        if (method == null) throw new IllegalArgumentException("Method cannot be null");
        this.set("method", method);
    }

    public AdjustableMixinExtrasInjectorNode withMethod(UnaryOperator<String[]> method) {
        this.setMethod(method.apply(this.getMethod()));
        return this;
    }

    public List<AdjustableAtNode> getAt() {
        return this.<List<AnnotationNode>>get("at")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableAtNode::new))
                .orElse(null);
    }

    public void setAt(List<AdjustableAtNode> at) {
        if (at == null) throw new IllegalArgumentException("At cannot be null");
        this.set("at", at);
    }

    public AdjustableMixinExtrasInjectorNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        this.setAt(at.apply(this.getAt()));
        return this;
    }

    public List<AdjustableSliceNode> getSlice() {
        return this.<List<AnnotationNode>>get("slice")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableSliceNode::new))
                .orElse(new ArrayList<>());
    }

    public void setSlice(List<AdjustableSliceNode> slice) {
        this.set("slice", slice);
    }

    public AdjustableMixinExtrasInjectorNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        this.setSlice(slice.apply(this.getSlice()));
        return this;
    }

    public boolean getRemap() {
        return this.<Boolean>get("remap").orElse(false);
    }

    public void setRemap(boolean remap) {
        this.set("remap", remap);
    }

    public AdjustableMixinExtrasInjectorNode withRemap(UnaryOperator<Boolean> remap) {
        this.setRemap(remap.apply(this.getRemap()));
        return this;
    }

    public int getRequire() {
        return this.<Integer>get("require").orElse(-1);
    }

    public void setRequire(int require) {
        this.set("require", require);
    }

    public AdjustableMixinExtrasInjectorNode withRequire(UnaryOperator<Integer> require) {
        this.setRequire(require.apply(this.getRequire()));
        return this;
    }

    public int getExpect() {
        return this.<Integer>get("expect").orElse(-1);
    }

    public void setExpect(int expect) {
        this.set("expect", expect);
    }

    public AdjustableMixinExtrasInjectorNode withExpect(UnaryOperator<Integer> expect) {
        this.setExpect(expect.apply(this.getExpect()));
        return this;
    }

    public int getAllow() {
        return this.<Integer>get("allow").orElse(-1);
    }

    public void setAllow(int allow) {
        this.set("allow", allow);
    }

    public AdjustableMixinExtrasInjectorNode withAllow(UnaryOperator<Integer> allow) {
        this.setAllow(allow.apply(this.getAllow()));
        return this;
    }
}
