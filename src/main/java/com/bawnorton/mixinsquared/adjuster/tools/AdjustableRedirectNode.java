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

import com.bawnorton.mixinsquared.adjuster.tools.type.AtAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.RemappableAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.SliceAnnotationNode;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.AnnotationNode;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class AdjustableRedirectNode extends AdjustableInjectorNode implements SliceAnnotationNode, AtAnnotationNode {
    public AdjustableRedirectNode(AnnotationNode node) {
        super(node);
    }

    public AdjustableRedirectNode defaultNode(AdjustableAtNode atNode) {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.REDIRECT.desc());
        AdjustableRedirectNode defaultNode = new AdjustableRedirectNode(node);
        defaultNode.setAt(atNode);
        return defaultNode;
    }

    @Override
    public AdjustableRedirectNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
        return (AdjustableRedirectNode) SliceAnnotationNode.super.withSlice(slice);
    }

    public AdjustableRedirectNode withAt(UnaryOperator<AdjustableAtNode> at) {
        return (AdjustableRedirectNode) AtAnnotationNode.super.withAt(at);
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

    @Override
    @ApiStatus.Internal
    public void applyRefmap(UnaryOperator<String> refmapApplicator) {
        super.applyRefmap(refmapApplicator);
        SliceAnnotationNode.super.applyRefmap(refmapApplicator);
        AtAnnotationNode.super.applyRefmap(refmapApplicator);
    }

    @Override
    public void setRemapper(Consumer<RemappableAnnotationNode> remapper) {
        super.setRemapper(remapper);
        SliceAnnotationNode.super.setRemapper(remapper);
        AtAnnotationNode.super.setRemapper(remapper);
    }
}
