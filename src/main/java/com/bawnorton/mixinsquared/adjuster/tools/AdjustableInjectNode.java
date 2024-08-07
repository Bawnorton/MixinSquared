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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableInjectNode extends AdjustableInjectorNode {
    public AdjustableInjectNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableInjectNode defaultNode(AdjustableAtNode... atNodes) {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.INJECT.desc());
        AdjustableInjectNode defaultNode = new AdjustableInjectNode(node);
        defaultNode.setAt(new ArrayList<AdjustableAtNode>() {{
            this.addAll(Arrays.asList(atNodes));
        }});
        return defaultNode;
    }

    public String getId() {
        return this.<String>get("id").orElse("");
    }

    public void setId(String id) {
        this.set("id", id);
    }

    public AdjustableInjectNode withId(UnaryOperator<String> id) {
        this.setId(id.apply(this.getId()));
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

    public AdjustableInjectNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        this.setSlice(slice.apply(this.getSlice()));
        return this;
    }

    public List<AdjustableAtNode> getAt() {
        return this.<List<AnnotationNode>>get("at")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableAtNode::new))
                .orElse(new ArrayList<>());
    }

    public void setAt(List<AdjustableAtNode> at) {
        this.set("at", at);
    }

    public AdjustableInjectNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        this.setAt(at.apply(this.getAt()));
        return this;
    }

    public boolean getCancellable() {
        return this.<Boolean>get("cancellable").orElse(false);
    }

    public void setCancellable(boolean cancellable) {
        this.set("cancellable", cancellable);
    }

    public AdjustableInjectNode withCancellable(UnaryOperator<Boolean> cancellable) {
        this.setCancellable(cancellable.apply(this.getCancellable()));
        return this;
    }

    public LocalCapture getLocals() {
        return this.getEnum("locals", LocalCapture.class).orElse(LocalCapture.NO_CAPTURE);
    }

    public void setLocals(LocalCapture locals) {
        this.set("locals", locals);
    }

    public AdjustableInjectNode withLocals(UnaryOperator<LocalCapture> locals) {
        this.setLocals(locals.apply(this.getLocals()));
        return this;
    }

    @Override
    public AdjustableInjectNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableInjectNode) super.withMethod(method);
    }

    @Override
    public AdjustableInjectNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        return (AdjustableInjectNode) super.withTarget(target);
    }

    @Override
    public AdjustableInjectNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableInjectNode) super.withRemap(remap);
    }

    @Override
    public AdjustableInjectNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableInjectNode) super.withRequire(require);
    }

    @Override
    public AdjustableInjectNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableInjectNode) super.withExpect(expect);
    }

    @Override
    public AdjustableInjectNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableInjectNode) super.withAllow(allow);
    }

    @Override
    public AdjustableInjectNode withConstraints(UnaryOperator<String> constraints) {
        return (AdjustableInjectNode) super.withConstraints(constraints);
    }
}
