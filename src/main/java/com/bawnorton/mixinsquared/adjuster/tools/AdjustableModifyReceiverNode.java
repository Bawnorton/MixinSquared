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
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyReceiverNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableModifyReceiverNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableModifyReceiverNode defaultNode(List<String> methods, List<AdjustableAtNode> atNodes) {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.MODIFY_RECIEVER.desc());
        AdjustableModifyReceiverNode defaultNode = new AdjustableModifyReceiverNode(node);
        defaultNode.setMethod(methods);
        defaultNode.setAt(atNodes);
        return defaultNode;
    }

    @Override
    public AdjustableModifyReceiverNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyReceiverNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyReceiverNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableModifyReceiverNode) super.withAt(at);
    }

    @Override
    public AdjustableModifyReceiverNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableModifyReceiverNode) super.withSlice(slice);
    }

    @Override
    public AdjustableModifyReceiverNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyReceiverNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyReceiverNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyReceiverNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyReceiverNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyReceiverNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyReceiverNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyReceiverNode) super.withAllow(allow);
    }
}
