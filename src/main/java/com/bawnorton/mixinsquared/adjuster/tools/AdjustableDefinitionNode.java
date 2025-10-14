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

import com.bawnorton.mixinsquared.adjuster.tools.type.MethodListAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.RequiredIdAnnotationNode;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableDefinitionNode extends RemapperHolderAnnotationNode implements MethodListAnnotationNode, RequiredIdAnnotationNode {
	public AdjustableDefinitionNode(AnnotationNode node) {
		super(node);
	}

	public static AdjustableDefinitionNode defaultNode(String id) {
		AnnotationNode node = new AnnotationNode(KnownAnnotations.DEFINITION.desc());
		AdjustableDefinitionNode defaultNode = new AdjustableDefinitionNode(node);
		defaultNode.setId(id);
		return defaultNode;
	}

	@Override
	public AdjustableDefinitionNode withId(UnaryOperator<String> id) {
		return (AdjustableDefinitionNode) RequiredIdAnnotationNode.super.withId(id);
	}

	public List<String> getField() {
		return this.<List<String>>get("field").orElse(new ArrayList<>());
	}

	public void setField(List<String> field) {
		this.set("field", field);
	}

	public AdjustableDefinitionNode withField(UnaryOperator<List<String>> field) {
		this.setField(field.apply(this.getField()));
		return this;
	}

	public List<Class<?>> getType() {
		return this.<List<Class<?>>>get("type").orElse(new ArrayList<>());
	}

	public void setType(List<Class<?>> type) {
		this.set("type", type);
	}

	public AdjustableDefinitionNode withType(UnaryOperator<List<Class<?>>> type) {
		this.setType(type.apply(this.getType()));
		return this;
	}

	public List<AdjustableLocalNode> getLocal() {
		return this.<List<AnnotationNode>>get("local")
				.map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableLocalNode::new))
				.orElse(new ArrayList<>());
	}

	public void setLocal(List<AdjustableLocalNode> locals) {
		this.set("local", locals);
	}

	public AdjustableDefinitionNode withLocal(UnaryOperator<List<AdjustableLocalNode>> locals) {
		this.setLocal(locals.apply(this.getLocal()));
		return this;
	}
}
