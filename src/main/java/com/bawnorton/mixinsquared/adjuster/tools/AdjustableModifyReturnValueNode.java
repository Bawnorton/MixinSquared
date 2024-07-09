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

public class AdjustableModifyReturnValueNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableModifyReturnValueNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableModifyReturnValueNode defaultNode(List<String> methods, List<AdjustableAtNode> atNodes) {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.MODIFY_RETURN_VALUE.desc());
        AdjustableModifyReturnValueNode defaultNode = new AdjustableModifyReturnValueNode(node);
        defaultNode.setMethod(methods);
        defaultNode.setAt(atNodes);
        return defaultNode;
    }

    @Override
    public AdjustableModifyReturnValueNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableModifyReturnValueNode) super.withMethod(method);
    }

    @Override
    public AdjustableModifyReturnValueNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableModifyReturnValueNode) super.withAt(at);
    }

    @Override
    public AdjustableModifyReturnValueNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableModifyReturnValueNode) super.withSlice(slice);
    }

    @Override
    public AdjustableModifyReturnValueNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableModifyReturnValueNode) super.withRemap(remap);
    }

    @Override
    public AdjustableModifyReturnValueNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableModifyReturnValueNode) super.withRequire(require);
    }

    @Override
    public AdjustableModifyReturnValueNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableModifyReturnValueNode) super.withExpect(expect);
    }

    @Override
    public AdjustableModifyReturnValueNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableModifyReturnValueNode) super.withAllow(allow);
    }
}
