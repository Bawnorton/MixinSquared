/*
 * MIT License
 *
 * Copyright (c) 2025-present Bawnorton
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

import com.bawnorton.mixinsquared.adjuster.tools.type.NthTargetAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.PrintableAnnotationNode;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableLocalNode extends AdjustableAnnotationNode implements PrintableAnnotationNode, NthTargetAnnotationNode {
	public AdjustableLocalNode(AnnotationNode node) {
		super(node);
	}

	public static AdjustableLocalNode defaultNode() {
		AnnotationNode node = new AnnotationNode(KnownAnnotations.LOCAL.desc());
		return new AdjustableLocalNode(node);
	}

	@Override
	public AdjustableLocalNode withPrint(UnaryOperator<Boolean> print) {
		return (AdjustableLocalNode) PrintableAnnotationNode.super.withPrint(print);
	}

	@Override
	public AdjustableLocalNode withOrdinal(UnaryOperator<Integer> ordinal) {
		return (AdjustableLocalNode) NthTargetAnnotationNode.super.withOrdinal(ordinal);
	}

	@Override
	public AdjustableLocalNode withIndex(UnaryOperator<Integer> index) {
		return (AdjustableLocalNode) NthTargetAnnotationNode.super.withIndex(index);
	}

	public List<String> getName() {
		return this.<List<String>>get("name").orElse(new ArrayList<>());
	}

	public void setName(List<String> name) {
		this.set("name", name);
	}

	public AdjustableLocalNode withName(UnaryOperator<List<String>> name) {
		this.setName(name.apply(this.getName()));
		return this;
	}

	public boolean getArgsOnly() {
		return this.<Boolean>get("argsOnly").orElse(false);
	}

	public void setArgsOnly(boolean argsOnly) {
		this.set("argsOnly", argsOnly);
	}

	public AdjustableLocalNode withArgsOnly(UnaryOperator<Boolean> argsOnly) {
		this.setArgsOnly(argsOnly.apply(this.getArgsOnly()));
		return this;
	}

	public Class<?> getType() {
		return this.<Class<?>>get("type").orElse(void.class);
	}

	public void setType(Class<?> type) {
		this.set("type", type);
	}

	public AdjustableLocalNode withType(UnaryOperator<Class<?>> type) {
		this.setType(type.apply(this.getType()));
		return this;
	}
}
