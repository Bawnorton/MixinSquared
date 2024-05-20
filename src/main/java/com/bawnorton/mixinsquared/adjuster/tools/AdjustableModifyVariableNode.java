/*
 * MIT License
 *
 * Copyright (c) 2023-present Bawnorton
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyVariableNode extends AdjustableInjectorNode {
    public AdjustableModifyVariableNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyVariable.class;
    }

    public static AdjustableModifyVariableNode defaultNode(AdjustableAtNode atNode) {
        AnnotationNode node = new AnnotationNode(ModifyVariable.class.getCanonicalName());
        AdjustableModifyVariableNode defaultNode = new AdjustableModifyVariableNode(node);
        defaultNode.setAt(atNode);
        return defaultNode;
    }

    public AdjustableSliceNode getSlice() {
        return this.<AnnotationNode>get("slice")
                .map(AdjustableSliceNode::new)
                .orElse(AdjustableSliceNode.defaultNode());
    }

    public void setSlice(AdjustableSliceNode slice) {
        this.set("slice", slice);
    }

    public AdjustableModifyVariableNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
        this.setSlice(slice.apply(this.getSlice()));
        return this;
    }

    public AdjustableAtNode getAt() {
        return this.<AnnotationNode>get("at")
                .map(AdjustableAtNode::new)
                .orElse(null);
    }

    public void setAt(AdjustableAtNode at) {
        if (at == null) throw new IllegalArgumentException("At cannot be null");
        this.set("at", at);
    }

    public AdjustableModifyVariableNode withAt(UnaryOperator<AdjustableAtNode> at) {
        this.setAt(at.apply(this.getAt()));
        return this;
    }

    public boolean getPrint() {
        return this.<Boolean>get("print").orElse(false);
    }
    
    public void setPrint(boolean print) {
        this.set("print", print);
    }
    
    public AdjustableModifyVariableNode withPrint(UnaryOperator<Boolean> print) {
        this.setPrint(print.apply(this.getPrint()));
        return this;
    }

    public int getOrdinal() {
        return this.<Integer>get("ordinal").orElse(-1);
    }
    
    public void setOrdinal(int ordinal) {
        this.set("ordinal", ordinal);
    }
    
    public AdjustableModifyVariableNode withOrdinal(UnaryOperator<Integer> ordinal) {
        this.setOrdinal(ordinal.apply(this.getOrdinal()));
        return this;
    }

    public int getIndex() {
        return this.<Integer>get("index").orElse(-1);
    }
    
    public void setIndex(int index) {
        this.set("index", index);
    }
    
    public AdjustableModifyVariableNode withIndex(UnaryOperator<Integer> index) {
        this.setIndex(index.apply(this.getIndex()));
        return this;
    }

    public List<String> getName() {
        return this.<List<String>>get("name").orElse(new ArrayList<>());
    }
    
    public void setName(List<String> name) {
        this.set("name", name);
    }
    
    public AdjustableModifyVariableNode withName(UnaryOperator<List<String>> name) {
        this.setName(name.apply(this.getName()));
        return this;
    }

    public boolean getArgsOnly() {
        return this.<Boolean>get("argsOnly").orElse(false);
    }
    
    public void setArgsOnly(boolean argsOnly) {
        this.set("argsOnly", argsOnly);
    }
    
    public AdjustableModifyVariableNode withArgsOnly(UnaryOperator<Boolean> argsOnly) {
        this.setArgsOnly(argsOnly.apply(this.getArgsOnly()));
        return this;
    }

    @Override
    public AdjustableModifyVariableNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyVariableNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyVariableNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        return (AdjustableModifyVariableNode) super.withTarget(target);
    }

    @Override
    public AdjustableModifyVariableNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyVariableNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyVariableNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyVariableNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyVariableNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyVariableNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyVariableNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyVariableNode) super.withAllow(allow);
    }

    @Override
    public AdjustableModifyVariableNode withConstraints(UnaryOperator<String> constraints) {
        return (AdjustableModifyVariableNode) super.withConstraints(constraints);
    }
}
