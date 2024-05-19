package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.tree.AnnotationNode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public abstract class AdjustableInjectorNode extends AdjustableAnnotationNode {
    protected AdjustableInjectorNode(AnnotationNode node) {
        super(node);
    }

    public List<String> getMethod() {
        return this.<List<String>>get("method").orElse(new ArrayList<>());
    }

    public void setMethod(List<String> method) {
        this.set("method", method);
    }

    public AdjustableInjectorNode withMethod(UnaryOperator<List<String>> method) {
        this.setMethod(method.apply(this.getMethod()));
        return this;
    }

    public List<AdjustableDescNode> getTarget() {
        return this.<List<AnnotationNode>>get("target")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableDescNode::new))
                .orElse(new ArrayList<>());
    }

    public void setTarget(List<AdjustableDescNode> target) {
        this.set("target", target);
    }

    public AdjustableInjectorNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        this.setTarget(target.apply(this.getTarget()));
        return this;
    }

    public boolean getRemap() {
        return this.<Boolean>get("remap").orElse(true);
    }

    public void setRemap(boolean remap) {
        this.set("remap", remap);
    }

    public AdjustableInjectorNode withRemap(UnaryOperator<Boolean> remap) {
        this.setRemap(remap.apply(this.getRemap()));
        return this;
    }

    public int getRequire() {
        return this.<Integer>get("require").orElse(-1);
    }

    public void setRequire(int require) {
        this.set("require", require);
    }

    public AdjustableInjectorNode withRequire(UnaryOperator<Integer> require) {
        this.setRequire(require.apply(this.getRequire()));
        return this;
    }

    public int getExpect() {
        return this.<Integer>get("expect").orElse(-1);
    }

    public void setExpect(int expect) {
        this.set("expect", expect);
    }

    public AdjustableInjectorNode withExpect(UnaryOperator<Integer> expect) {
        this.setExpect(expect.apply(this.getExpect()));
        return this;
    }

    public int getAllow() {
        return this.<Integer>get("allow").orElse(-1);
    }

    public void setAllow(int allow) {
        this.set("allow", allow);
    }

    public AdjustableInjectorNode withAllow(UnaryOperator<Integer> allow) {
        this.setAllow(allow.apply(this.getAllow()));
        return this;
    }

    public String getConstraints() {
        return this.<String>get("constraints").orElse("");
    }

    public void setConstraints(String constraints) {
        this.set("constraints", constraints);
    }

    public AdjustableInjectorNode withConstraints(UnaryOperator<String> constraints) {
        this.setConstraints(constraints.apply(this.getConstraints()));
        return this;
    }
}
