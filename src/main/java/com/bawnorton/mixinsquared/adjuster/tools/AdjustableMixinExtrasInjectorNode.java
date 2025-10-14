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
import com.bawnorton.mixinsquared.adjuster.tools.type.MatchCountAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.MethodListAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.SliceListAnnotationNode;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class AdjustableMixinExtrasInjectorNode extends RemapperHolderAnnotationNode implements MethodListAnnotationNode, SliceListAnnotationNode, AtListAnnotationNode, MatchCountAnnotationNode {
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

	@Override
	public AdjustableMixinExtrasInjectorNode withRequire(UnaryOperator<Integer> require) {
		return (AdjustableMixinExtrasInjectorNode) MatchCountAnnotationNode.super.withRequire(require);
	}

	@Override
	public AdjustableMixinExtrasInjectorNode withExpect(UnaryOperator<Integer> expect) {
		return (AdjustableMixinExtrasInjectorNode) MatchCountAnnotationNode.super.withExpect(expect);
	}

	@Override
	public AdjustableMixinExtrasInjectorNode withAllow(UnaryOperator<Integer> allow) {
		return (AdjustableMixinExtrasInjectorNode) MatchCountAnnotationNode.super.withAllow(allow);
	}

	@Override
	@ApiStatus.Internal
	public void applyRefmap(UnaryOperator<String> refmapApplicator) {
		MethodListAnnotationNode.super.applyRefmap(refmapApplicator);
		SliceListAnnotationNode.super.applyRefmap(refmapApplicator);
		AtListAnnotationNode.super.applyRefmap(refmapApplicator);
	}
}
