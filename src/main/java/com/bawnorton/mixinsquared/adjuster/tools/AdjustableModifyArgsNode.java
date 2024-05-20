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

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyArgsNode extends AdjustableInjectorNode {
    public AdjustableModifyArgsNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ModifyArgs.class;
    }

    public static AdjustableModifyArgsNode defaultNode(AdjustableAtNode atNode) {
        AnnotationNode node = new AnnotationNode(Type.getDescriptor(ModifyArgs.class));
        AdjustableModifyArgsNode defaultNode = new AdjustableModifyArgsNode(node);
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

    public AdjustableModifyArgsNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
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

    public AdjustableModifyArgsNode withAt(UnaryOperator<AdjustableAtNode> at) {
        this.setAt(at.apply(this.getAt()));
        return this;
    }

    @Override
    public AdjustableModifyArgsNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyArgsNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyArgsNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        return (AdjustableModifyArgsNode) super.withTarget(target);
    }

    @Override
    public AdjustableModifyArgsNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyArgsNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyArgsNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyArgsNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyArgsNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyArgsNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyArgsNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyArgsNode) super.withAllow(allow);
    }

    @Override
    public AdjustableModifyArgsNode withConstraints(UnaryOperator<String> constraints) {
        return (AdjustableModifyArgsNode) super.withConstraints(constraints);
    }
}
