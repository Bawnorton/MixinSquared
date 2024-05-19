package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.Next;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableNextNode extends AdjustableAnnotationNode {
    public AdjustableNextNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return Next.class;
    }

    public String getName() {
        return this.<String>get("name").orElse("");
    }

    public void setName(String name) {
        this.set("name", name);
    }

    public AdjustableNextNode withName(UnaryOperator<String> name) {
        this.setName(name.apply(this.getName()));
        return this;
    }

    public Type getRet() {
        return this.<Type>get("ret").orElse(Type.VOID_TYPE);
    }

    public void setRet(Type ret) {
        this.set("ret", ret);
    }

    public AdjustableNextNode withRet(UnaryOperator<Type> ret) {
        this.setRet(ret.apply(this.getRet()));
        return this;
    }

    public List<Type> getArgs() {
        return this.<List<Type>>get("args").orElse(new ArrayList<>());
    }

    public void setArgs(List<Type> args) {
        this.set("args", args);
    }

    public AdjustableNextNode withArgs(UnaryOperator<List<Type>> args) {
        this.setArgs(args.apply(this.getArgs()));
        return this;
    }

    public int getMin() {
        return this.<Integer>get("min").orElse(0);
    }

    public void setMin(int min) {
        this.set("min", min);
    }

    public AdjustableNextNode withMin(UnaryOperator<Integer> min) {
        this.setMin(min.apply(this.getMin()));
        return this;
    }

    public int getMax() {
        return this.<Integer>get("max").orElse(Integer.MAX_VALUE);
    }

    public void setMax(int max) {
        this.set("max", max);
    }

    public AdjustableNextNode withMax(UnaryOperator<Integer> max) {
        this.setMax(max.apply(this.getMax()));
        return this;
    }
}
