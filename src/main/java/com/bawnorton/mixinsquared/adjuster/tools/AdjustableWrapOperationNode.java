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

public class AdjustableWrapOperationNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableWrapOperationNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableWrapOperationNode defaultNode(List<String> methods) {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.WRAP_OPERATION.desc());
        AdjustableWrapOperationNode defaultNode = new AdjustableWrapOperationNode(node);
        defaultNode.setMethod(methods);
        return defaultNode;
    }

    @Override
    public List<AdjustableAtNode> getAt() {
        List<AdjustableAtNode> at = super.getAt();
        return at == null ? new ArrayList<>() : at;
    }

    public List<AdjustableConstantNode> getConstant() {
        return this.<List<AnnotationNode>>get("constants")
                   .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableConstantNode::new))
                   .orElse(new ArrayList<>());
    }

    public void setConstant(List<AdjustableConstantNode> constants) {
        this.set("constant", constants);
    }

    public AdjustableWrapOperationNode withConstant(UnaryOperator<List<AdjustableConstantNode>> constants) {
        this.setConstant(constants.apply(this.getConstant()));
        return this;
    }

    @Override
    public AdjustableWrapOperationNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableWrapOperationNode) super.withMethod(method);
    }

    @Override
    public AdjustableWrapOperationNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableWrapOperationNode) super.withAt(at);
    }

    @Override
    public AdjustableWrapOperationNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableWrapOperationNode) super.withSlice(slice);
    }

    @Override
    public AdjustableWrapOperationNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableWrapOperationNode) super.withRemap(remap);
    }

    @Override
    public AdjustableWrapOperationNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableWrapOperationNode) super.withRequire(require);
    }

    @Override
    public AdjustableWrapOperationNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableWrapOperationNode) super.withExpect(expect);
    }

    @Override
    public AdjustableWrapOperationNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableWrapOperationNode) super.withAllow(allow);
    }
}
