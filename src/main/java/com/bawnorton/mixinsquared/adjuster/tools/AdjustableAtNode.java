package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.At;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableAtNode extends AdjustableAnnotationNode {
    public AdjustableAtNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return At.class;
    }

    public String getId() {
        return this.<String>get("id").orElse("");
    }

    public void setId(String id) {
        this.set("id", id);
    }

    public AdjustableAtNode withId(UnaryOperator<String> id) {
        this.setId(id.apply(this.getId()));
        return this;
    }

    public String getValue() {
        return this.<String>get("value").orElse(null);
    }

    public void setValue(String value) {
        if(value == null) throw new IllegalArgumentException("Value cannot be null");
        this.set("value", value);
    }

    public AdjustableAtNode withValue(UnaryOperator<String> value) {
        this.setValue(value.apply(this.getValue()));
        return this;
    }

    public String getSlice() {
        return this.<String>get("slice").orElse("");
    }

    public void setSlice(String slice) {
        this.set("slice", slice);
    }

    public AdjustableAtNode withSlice(UnaryOperator<String> slice) {
        this.setSlice(slice.apply(this.getSlice()));
        return this;
    }

    public At.Shift getShift() {
        return this.getEnum("shift", At.Shift.class).orElse(At.Shift.NONE);
    }

    public void setShift(At.Shift shift) {
        this.set("shift", new String[] {Type.getDescriptor(At.Shift.class), shift.name()});
    }

    public AdjustableAtNode withShift(UnaryOperator<At.Shift> shift) {
        this.setShift(shift.apply(this.getShift()));
        return this;
    }

    public int getBy() {
        return this.<Integer>get("by").orElse(0);
    }

    public void setBy(int by) {
        this.set("by", by);
    }

    public AdjustableAtNode withBy(UnaryOperator<Integer> by) {
        this.setBy(by.apply(this.getBy()));
        return this;
    }

    public List<String> getArgs() {
        return this.<List<String>>get("args").orElse(new ArrayList<>());
    }

    public void setArgs(List<String> args) {
        this.set("args", args);
    }

    public AdjustableAtNode withArgs(UnaryOperator<List<String>> args) {
        this.setArgs(args.apply(this.getArgs()));
        return this;
    }

    public String getTarget() {
        return this.<String>get("target").orElse("");
    }

    public void setTarget(String target) {
        this.set("target", target);
    }

    public AdjustableAtNode withTarget(UnaryOperator<String> target) {
        this.setTarget(target.apply(this.getTarget()));
        return this;
    }

    public AdjustableDescNode getDesc() {
        return this.<AnnotationNode>get("desc")
                .map(AdjustableDescNode::new)
                .orElse(AdjustableDescNode.DEFAULT);
    }

    public void setDesc(AdjustableDescNode desc) {
        this.set("desc", desc);
    }

    public AdjustableAtNode withDesc(UnaryOperator<AdjustableDescNode> desc) {
        this.setDesc(desc.apply(this.getDesc()));
        return this;
    }

    public int getOrdinal() {
        return this.<Integer>get("ordinal").orElse(-1);
    }

    public void setOrdinal(int ordinal) {
        this.set("ordinal", ordinal);
    }

    public AdjustableAtNode withOrdinal(UnaryOperator<Integer> ordinal) {
        this.setOrdinal(ordinal.apply(this.getOrdinal()));
        return this;
    }

    public int getOpcode() {
        return this.<Integer>get("opcode").orElse(-1);
    }

    public void setOpcode(int opcode) {
        this.set("opcode", opcode);
    }

    public AdjustableAtNode withOpcode(UnaryOperator<Integer> opcode) {
        this.setOpcode(opcode.apply(this.getOpcode()));
        return this;
    }

    public boolean getRemap() {
        return this.<Boolean>get("remap").orElse(true);
    }

    public void setRemap(boolean remap) {
        this.set("remap", remap);
    }

    public AdjustableAtNode withRemap(UnaryOperator<Boolean> remap) {
        this.setRemap(remap.apply(this.getRemap()));
        return this;
    }

    public boolean getUnsafe() {
        return this.<Boolean>get("unsafe").orElse(false);
    }

    public void setUnsafe(boolean unsafe) {
        this.set("unsafe", unsafe);
    }

    public AdjustableAtNode withUnsafe(UnaryOperator<Boolean> unsafe) {
        this.setUnsafe(unsafe.apply(this.getUnsafe()));
        return this;
    }

    enum InjectionPoint {
        HEAD,
        TAIL,
        INVOKE,
        INVOKE_ASSIGN,
        INVOKE_STRING,
        FIELD,
        NEW,
        CONSTANT,
        JUMP;

        public AdjustableAtNode toNode() {
            AnnotationNode node = new AnnotationNode(Type.getDescriptor(At.class));
            node.visit("value", this.name().toUpperCase());
            return new AdjustableAtNode(node);
        }
    }
}
