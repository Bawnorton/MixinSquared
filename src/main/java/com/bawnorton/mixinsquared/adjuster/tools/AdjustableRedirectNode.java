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
import org.spongepowered.asm.mixin.injection.Redirect;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableRedirectNode extends AdjustableInjectorNode {
    public AdjustableRedirectNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return Redirect.class;
    }

    public AdjustableRedirectNode defaultNode(AdjustableAtNode atNode) {
        AnnotationNode node = new AnnotationNode(Redirect.class.getCanonicalName());
        AdjustableRedirectNode defaultNode = new AdjustableRedirectNode(node);
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

    public AdjustableRedirectNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
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

    public AdjustableRedirectNode withAt(UnaryOperator<AdjustableAtNode> at) {
        this.setAt(at.apply(this.getAt()));
        return this;
    }

    @Override
    public AdjustableRedirectNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableRedirectNode) super.withMethod(method);
    }

    @Override
    public AdjustableRedirectNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        return (AdjustableRedirectNode) super.withTarget(target);
    }

    @Override
    public AdjustableRedirectNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableRedirectNode) super.withRemap(remap);
    }

    @Override
    public AdjustableRedirectNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableRedirectNode) super.withRequire(require);
    }

    @Override
    public AdjustableRedirectNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableRedirectNode) super.withExpect(expect);
    }

    @Override
    public AdjustableRedirectNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableRedirectNode) super.withAllow(allow);
    }

    @Override
    public AdjustableRedirectNode withConstraints(UnaryOperator<String> constraints) {
        return (AdjustableRedirectNode) super.withConstraints(constraints);
    }
}
