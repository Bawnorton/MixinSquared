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

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableWrapWithConditionNode extends AdjustableMixinExtrasInjectorNode {
    public AdjustableWrapWithConditionNode(AnnotationNode node) {
        super(node);
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return WrapWithCondition.class;
    }

    public static AdjustableWrapWithConditionNode defaultNode() {
        AnnotationNode node = new AnnotationNode(Type.getDescriptor(WrapWithCondition.class));
        return new AdjustableWrapWithConditionNode(node);
    }

    @Override
    public AdjustableWrapWithConditionNode withMethod(UnaryOperator<List<String>> method) {
        return (AdjustableWrapWithConditionNode) super.withMethod(method);
    }

    @Override
    public AdjustableWrapWithConditionNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        return (AdjustableWrapWithConditionNode) super.withAt(at);
    }

    @Override
    public AdjustableWrapWithConditionNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        return (AdjustableWrapWithConditionNode) super.withSlice(slice);
    }

    @Override
    public AdjustableWrapWithConditionNode withRemap(UnaryOperator<Boolean> remap) {
        return (AdjustableWrapWithConditionNode) super.withRemap(remap);
    }

    @Override
    public AdjustableWrapWithConditionNode withRequire(UnaryOperator<Integer> require) {
        return (AdjustableWrapWithConditionNode) super.withRequire(require);
    }

    @Override
    public AdjustableWrapWithConditionNode withExpect(UnaryOperator<Integer> expect) {
        return (AdjustableWrapWithConditionNode) super.withExpect(expect);
    }

    @Override
    public AdjustableWrapWithConditionNode withAllow(UnaryOperator<Integer> allow) {
        return (AdjustableWrapWithConditionNode) super.withAllow(allow);
    }
}
