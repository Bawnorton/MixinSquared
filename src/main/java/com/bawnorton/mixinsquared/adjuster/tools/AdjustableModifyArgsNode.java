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
import com.bawnorton.mixinsquared.adjuster.tools.type.AtListAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.MethodListAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.RemappableAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.SliceAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.SliceListAnnotationNode;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.AnnotationNode;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class AdjustableModifyArgsNode extends AdjustableInjectorNode implements SliceAnnotationNode, AtAnnotationNode {
    public AdjustableModifyArgsNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableModifyArgsNode defaultNode(AdjustableAtNode atNode) {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.MODIFY_ARGS.desc());
        AdjustableModifyArgsNode defaultNode = new AdjustableModifyArgsNode(node);
        defaultNode.setAt(atNode);
        return defaultNode;
    }

    @Override
    public AdjustableModifyArgsNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
        return (AdjustableModifyArgsNode) SliceAnnotationNode.super.withSlice(slice);
    }

    public AdjustableModifyArgsNode withAt(UnaryOperator<AdjustableAtNode> at) {
        return (AdjustableModifyArgsNode) AtAnnotationNode.super.withAt(at);
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
