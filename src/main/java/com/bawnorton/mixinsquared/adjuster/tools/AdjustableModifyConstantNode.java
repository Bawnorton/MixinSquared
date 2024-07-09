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
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyConstantNode extends AdjustableInjectorNode {
    public AdjustableModifyConstantNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableModifyConstantNode defaultNode() {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.MODIFY_CONSTANT.desc());
        return new AdjustableModifyConstantNode(node);
    }

    public List<AdjustableSliceNode> getSlice() {
        return this.<List<AnnotationNode>>get("slice")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableSliceNode::new))
                .orElse(new ArrayList<>());
    }

    public void setSlice(List<AdjustableSliceNode> slice) {
        this.set("slice", slice);
    }

    public AdjustableModifyConstantNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        this.setSlice(slice.apply(this.getSlice()));
        return this;
    }

    public List<AdjustableConstantNode> getConstant() {
        return this.<List<AnnotationNode>>get("constant")
                .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableConstantNode::new))
                .orElse(new ArrayList<>());
    }

    public void setConstant(List<AdjustableConstantNode> constant) {
        this.set("constant", constant);
    }

    public AdjustableModifyConstantNode withConstant(UnaryOperator<List<AdjustableConstantNode>> constant) {
        this.setConstant(constant.apply(this.getConstant()));
        return this;
    }

    @Override
    public AdjustableModifyConstantNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyConstantNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyConstantNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
        return (AdjustableModifyConstantNode) super.withTarget(target);
    }

    @Override
    public AdjustableModifyConstantNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyConstantNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyConstantNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyConstantNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyConstantNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyConstantNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyConstantNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyConstantNode) super.withAllow(allow);
    }

    @Override
    public AdjustableModifyConstantNode withConstraints(UnaryOperator<String> constraints) {
        return (AdjustableModifyConstantNode) super.withConstraints(constraints);
    }
}
