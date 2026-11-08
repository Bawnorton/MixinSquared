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

import com.bawnorton.mixinsquared.adjuster.tools.type.IndexedAnnotationNode;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyArgNode extends AdjustableModifyArgsNode implements IndexedAnnotationNode {
	public AdjustableModifyArgNode(AnnotationNode node) {
		super(node);
	}

	public static AdjustableModifyArgNode defaultNode(AdjustableAtNode atNode) {
		AnnotationNode node = new AnnotationNode(KnownAnnotations.MODIFY_ARG.desc());
		AdjustableModifyArgNode defaultNode = new AdjustableModifyArgNode(node);
		defaultNode.setAt(atNode);
		return defaultNode;
	}

	@Override
	public AdjustableModifyArgNode withIndex(UnaryOperator<Integer> index) {
		return (AdjustableModifyArgNode) IndexedAnnotationNode.super.withIndex(index);
	}

	/**
	 * @apiNote If using fabric mixin >=0.17.4 prefer {@link AdjustableModifyArgNode#withSlices(UnaryOperator)}
	 */
	@Override
	public AdjustableModifyArgNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
		return (AdjustableModifyArgNode) super.withSlice(slice);
	}

	/**
	 * @apiNote If using anything other than fabric mixin >=0.17.4 prefer {@link AdjustableModifyArgNode#withSlice(UnaryOperator)}
	 */
	@Override
	public AdjustableModifyArgNode withSlices(UnaryOperator<List<AdjustableSliceNode>> slices) {
		return (AdjustableModifyArgNode) super.withSlices(slices);
	}

	/**
	 * @apiNote If using fabric mixin >=0.17.4 prefer {@link AdjustableModifyArgNode#withAts(UnaryOperator)}
	 */
	@Override
	public AdjustableModifyArgNode withAt(UnaryOperator<AdjustableAtNode> at) {
		return (AdjustableModifyArgNode) super.withAt(at);
	}

	/**
	 * @apiNote If using anything other than fabric mixin >=0.17.4 prefer {@link AdjustableModifyArgNode#withAt(UnaryOperator)}
	 */
	@Override
	public AdjustableModifyArgNode withAts(UnaryOperator<List<AdjustableAtNode>> ats) {
		return (AdjustableModifyArgNode) super.withAts(ats);
	}

	@Override
	public AdjustableModifyArgNode withMethod(UnaryOperator<List<String>> method) {
		return (AdjustableModifyArgNode) super.withMethod(method);
	}

	@Override
	public AdjustableModifyArgNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
		return (AdjustableModifyArgNode) super.withTarget(target);
	}

	@Override
	public AdjustableModifyArgNode withRemap(UnaryOperator<Boolean> remap) {
		return (AdjustableModifyArgNode) super.withRemap(remap);
	}

	@Override
	public AdjustableModifyArgNode withRequire(UnaryOperator<Integer> require) {
		return (AdjustableModifyArgNode) super.withRequire(require);
	}

	@Override
	public AdjustableModifyArgNode withExpect(UnaryOperator<Integer> expect) {
		return (AdjustableModifyArgNode) super.withExpect(expect);
	}

	@Override
	public AdjustableModifyArgNode withAllow(UnaryOperator<Integer> allow) {
		return (AdjustableModifyArgNode) super.withAllow(allow);
	}

	@Override
	public AdjustableModifyArgNode withConstraints(UnaryOperator<String> constraints) {
		return (AdjustableModifyArgNode) super.withConstraints(constraints);
	}

	@Override
	public AdjustableModifyArgNode withOrder(UnaryOperator<Integer> order) {
		return (AdjustableModifyArgNode) super.withOrder(order);
	}
}
