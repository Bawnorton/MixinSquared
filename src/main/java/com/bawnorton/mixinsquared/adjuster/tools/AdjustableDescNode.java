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

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableDescNode extends AdjustableAnnotationNode {
	public AdjustableDescNode(AnnotationNode node) {
		super(node);
	}

	public static AdjustableDescNode defaultNode(String value) {
		AnnotationNode node = new AnnotationNode(KnownAnnotations.DESC.desc());
		AdjustableDescNode defaultNode = new AdjustableDescNode(node);
		defaultNode.setValue(value);
		return defaultNode;
	}

	public String getId() {
		return this.<String>get("id").orElse("");
	}

	public void setId(String id) {
		this.set("id", id);
	}

	public AdjustableDescNode withId(UnaryOperator<String> id) {
		this.setId(id.apply(this.getId()));
		return this;
	}

	public Type getOwner() {
		return this.<Type>get("owner").orElse(Type.VOID_TYPE);
	}

	public void setOwner(Type owner) {
		this.set("owner", owner);
	}

	public AdjustableDescNode withOwner(UnaryOperator<Type> owner) {
		this.setOwner(owner.apply(this.getOwner()));
		return this;
	}

	public String getValue() {
		return this.<String>get("value").orElse(null);
	}

	public void setValue(String value) {
		if (value == null) throw new IllegalArgumentException("Value cannot be null");
		this.set("value", value);
	}

	public AdjustableDescNode withValue(UnaryOperator<String> value) {
		this.setValue(value.apply(this.getValue()));
		return this;
	}

	public Type getRet() {
		return this.<Type>get("ret").orElse(Type.VOID_TYPE);
	}

	public void setRet(Type ret) {
		this.set("ret", ret);
	}

	public AdjustableDescNode withRet(UnaryOperator<Type> ret) {
		this.setRet(ret.apply(this.getRet()));
		return this;
	}

	public List<Type> getArgs() {
		return this.<List<Type>>get("args").orElse(new ArrayList<>());
	}

	public void setArgs(List<Type> args) {
		this.set("args", args);
	}

	public AdjustableDescNode withArgs(UnaryOperator<List<Type>> args) {
		this.setArgs(args.apply(this.getArgs()));
		return this;
	}

	public List<AdjustableNextNode> getNext() {
		return this.<List<AnnotationNode>>get("next")
				.map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableNextNode::new))
				.orElse(new ArrayList<>());
	}

	public void setNext(List<AdjustableNextNode> next) {
		this.set("next", next);
	}

	public AdjustableDescNode withNext(UnaryOperator<List<AdjustableNextNode>> next) {
		this.setNext(next.apply(this.getNext()));
		return this;
	}

	public int getMin() {
		return this.<Integer>get("min").orElse(0);
	}

	public void setMin(int min) {
		this.set("min", min);
	}

	public AdjustableDescNode withMin(UnaryOperator<Integer> min) {
		this.setMin(min.apply(this.getMin()));
		return this;
	}

	public int getMax() {
		return this.<Integer>get("max").orElse(Integer.MAX_VALUE);
	}

	public void setMax(int max) {
		this.set("max", max);
	}

	public AdjustableDescNode withMax(UnaryOperator<Integer> max) {
		this.setMax(max.apply(this.getMax()));
		return this;
	}
}
