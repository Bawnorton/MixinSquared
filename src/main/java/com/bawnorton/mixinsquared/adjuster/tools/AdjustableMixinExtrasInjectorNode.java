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

import com.bawnorton.mixinsquared.adjuster.tools.type.AtListAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.MethodListAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.RemappableAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.SliceListAnnotationNode;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.AnnotationNode;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public abstract class AdjustableMixinExtrasInjectorNode extends RemapperHolderAnnotationNode implements MethodListAnnotationNode, SliceListAnnotationNode, AtListAnnotationNode {
    protected AdjustableMixinExtrasInjectorNode(AnnotationNode node) {
        super(node);
    }

    @Override
    public AdjustableMixinExtrasInjectorNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableMixinExtrasInjectorNode) MethodListAnnotationNode.super.withMethod(method);
    }

    @Override
    public AdjustableMixinExtrasInjectorNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableMixinExtrasInjectorNode) AtListAnnotationNode.super.withAt(at);
    }

    @Override
    public AdjustableMixinExtrasInjectorNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableMixinExtrasInjectorNode) SliceListAnnotationNode.super.withSlice(slice);
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

    @Override
    @ApiStatus.Internal
    public void applyRefmap(UnaryOperator<String> refmapApplicator) {
        MethodListAnnotationNode.super.applyRefmap(refmapApplicator);
        SliceListAnnotationNode.super.applyRefmap(refmapApplicator);
        AtListAnnotationNode.super.applyRefmap(refmapApplicator);
    }

    @Override
    public void setRemapper(Consumer<RemappableAnnotationNode> remapper) {
        super.setRemapper(remapper);
        SliceListAnnotationNode.super.setRemapper(remapper);
        AtListAnnotationNode.super.setRemapper(remapper);
    }
}
