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
import com.bawnorton.mixinsquared.adjuster.tools.type.NthTargetAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.PrintableAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.SliceAnnotationNode;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableModifyVariableNode extends AdjustableInjectorNode implements SliceAnnotationNode, AtAnnotationNode, NthTargetAnnotationNode, PrintableAnnotationNode {
	public AdjustableModifyVariableNode(AnnotationNode node) {
		super(node);
	}

	public static AdjustableModifyVariableNode defaultNode(AdjustableAtNode atNode) {
		AnnotationNode node = new AnnotationNode(KnownAnnotations.MODIFY_VARIABLE.desc());
		AdjustableModifyVariableNode defaultNode = new AdjustableModifyVariableNode(node);
		defaultNode.setAt(atNode);
		return defaultNode;
	}

	public AdjustableModifyVariableNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
		return (AdjustableModifyVariableNode) SliceAnnotationNode.super.withSlice(slice);
	}

	public AdjustableModifyVariableNode withAt(UnaryOperator<AdjustableAtNode> at) {
		return (AdjustableModifyVariableNode) AtAnnotationNode.super.withAt(at);
	}

	public AdjustableModifyVariableNode withPrint(UnaryOperator<Boolean> print) {
		return (AdjustableModifyVariableNode) PrintableAnnotationNode.super.withPrint(print);
	}

	public AdjustableModifyVariableNode withOrdinal(UnaryOperator<Integer> ordinal) {
		return (AdjustableModifyVariableNode) NthTargetAnnotationNode.super.withOrdinal(ordinal);
	}

	public AdjustableModifyVariableNode withIndex(UnaryOperator<Integer> index) {
		return (AdjustableModifyVariableNode) NthTargetAnnotationNode.super.withIndex(index);
	}

	public List<String> getName() {
		return this.<List<String>>get("name").orElse(new ArrayList<>());
	}

	public void setName(List<String> name) {
		this.set("name", name);
	}

	public AdjustableModifyVariableNode withName(UnaryOperator<List<String>> name) {
		this.setName(name.apply(this.getName()));
		return this;
	}

	public boolean getArgsOnly() {
		return this.<Boolean>get("argsOnly").orElse(false);
	}

	public void setArgsOnly(boolean argsOnly) {
		this.set("argsOnly", argsOnly);
	}

	public AdjustableModifyVariableNode withArgsOnly(UnaryOperator<Boolean> argsOnly) {
		this.setArgsOnly(argsOnly.apply(this.getArgsOnly()));
		return this;
	}

	@Override
	public AdjustableModifyVariableNode withMethod(UnaryOperator<List<String>> method) {
		return (AdjustableModifyVariableNode) super.withMethod(method);
	}

	@Override
	public AdjustableModifyVariableNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
		return (AdjustableModifyVariableNode) super.withTarget(target);
	}

	@Override
	public AdjustableModifyVariableNode withRemap(UnaryOperator<Boolean> remap) {
		return (AdjustableModifyVariableNode) super.withRemap(remap);
	}

	@Override
	public AdjustableModifyVariableNode withRequire(UnaryOperator<Integer> require) {
		return (AdjustableModifyVariableNode) super.withRequire(require);
	}

	@Override
	public AdjustableModifyVariableNode withExpect(UnaryOperator<Integer> expect) {
		return (AdjustableModifyVariableNode) super.withExpect(expect);
	}

	@Override
	public AdjustableModifyVariableNode withAllow(UnaryOperator<Integer> allow) {
		return (AdjustableModifyVariableNode) super.withAllow(allow);
	}

	@Override
	public AdjustableModifyVariableNode withConstraints(UnaryOperator<String> constraints) {
		return (AdjustableModifyVariableNode) super.withConstraints(constraints);
	}

	@Override
	public AdjustableModifyVariableNode withOrder(UnaryOperator<Integer> order) {
		return (AdjustableModifyVariableNode) super.withOrder(order);
	}

	@Override
	@ApiStatus.Internal
	public void applyRefmap(UnaryOperator<String> refmapApplicator) {
		super.applyRefmap(refmapApplicator);
		SliceAnnotationNode.super.applyRefmap(refmapApplicator);
		AtAnnotationNode.super.applyRefmap(refmapApplicator);
	}
}
