package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.Overwrite;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableOverwriteNode extends AdjustableAnnotationNode {
    public AdjustableOverwriteNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return Overwrite.class;
    }

    public static AdjustableOverwriteNode defaultNode() {
        AnnotationNode node = new AnnotationNode(Type.getDescriptor(Overwrite.class));
        return new AdjustableOverwriteNode(node);
    }

    public String getConstraints() {
        return this.<String>get("constraints").orElse("");
    }

    public void setConstraints(String constraints) {
        this.set("constraints", constraints);
    }

    public AdjustableOverwriteNode withConstraints(UnaryOperator<String> constraints) {
        this.setConstraints(constraints.apply(this.getConstraints()));
        return this;
    }

    public List<String> getAliases() {
        return this.<List<String>>get("aliases").orElse(new ArrayList<>());
    }

    public void setAliases(List<String> aliases) {
        this.set("aliases", aliases);
    }

    public AdjustableOverwriteNode withAliases(UnaryOperator<List<String>> aliases) {
        this.setAliases(aliases.apply(this.getAliases()));
        return this;
    }

    public boolean getRemap() {
        return this.<Boolean>get("remap").orElse(true);
    }

    public void setRemap(boolean remap) {
        this.set("remap", remap);
    }

    public AdjustableOverwriteNode withRemap(UnaryOperator<Boolean> remap) {
        this.setRemap(remap.apply(this.getRemap()));
        return this;
    }
}
