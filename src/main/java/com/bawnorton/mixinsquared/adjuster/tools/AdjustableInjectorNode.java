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

import com.bawnorton.mixinsquared.adjuster.tools.type.ConstraintAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.MatchCountAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.MethodListAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.OrderAnnotationNode;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public abstract class AdjustableInjectorNode extends RemapperHolderAnnotationNode implements MethodListAnnotationNode, MatchCountAnnotationNode, ConstraintAnnotationNode, OrderAnnotationNode {
	protected AdjustableInjectorNode(AnnotationNode node) {
		super(node);
	}

	@Override
	public AdjustableInjectorNode withMethod(UnaryOperator<List<String>> method) {
		return (AdjustableInjectorNode) MethodListAnnotationNode.super.withMethod(method);
	}

	public List<AdjustableDescNode> getTarget() {
		return this.<List<AnnotationNode>>get("target")
				.map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableDescNode::new))
				.orElse(new ArrayList<>());
	}

	public void setTarget(List<AdjustableDescNode> target) {
		this.set("target", target);
	}

	public AdjustableInjectorNode withTarget(UnaryOperator<List<AdjustableDescNode>> target) {
		this.setTarget(target.apply(this.getTarget()));
		return this;
	}

	public AdjustableInjectorNode withRequire(UnaryOperator<Integer> require) {
		return (AdjustableInjectorNode) MatchCountAnnotationNode.super.withRequire(require);
	}

	public AdjustableInjectorNode withExpect(UnaryOperator<Integer> expect) {
		return (AdjustableInjectorNode) MatchCountAnnotationNode.super.withExpect(expect);
	}

	public AdjustableInjectorNode withAllow(UnaryOperator<Integer> allow) {
		return (AdjustableInjectorNode) MatchCountAnnotationNode.super.withAllow(allow);
	}

	public AdjustableInjectorNode withConstraints(UnaryOperator<String> constraints) {
		return (AdjustableInjectorNode) ConstraintAnnotationNode.super.withConstraints(constraints);
	}

	public AdjustableInjectorNode withOrder(UnaryOperator<Integer> order) {
		return (AdjustableInjectorNode) OrderAnnotationNode.super.withOrder(order);
	}
}
