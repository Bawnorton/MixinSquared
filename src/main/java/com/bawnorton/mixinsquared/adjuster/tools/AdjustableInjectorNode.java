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

import com.bawnorton.mixinsquared.adjuster.tools.type.MethodListAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.RemappableAnnotationNode;
import org.objectweb.asm.tree.AnnotationNode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public abstract class AdjustableInjectorNode extends RemapperHolderAnnotationNode implements MethodListAnnotationNode {
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

    public int getRequire() {
        return this.<Integer>get("require").orElse(-1);
    }

    public void setRequire(int require) {
        this.set("require", require);
    }

    public AdjustableInjectorNode withRequire(UnaryOperator<Integer> require) {
        this.setRequire(require.apply(this.getRequire()));
        return this;
    }

    public int getExpect() {
        return this.<Integer>get("expect").orElse(-1);
    }

    public void setExpect(int expect) {
        this.set("expect", expect);
    }

    public AdjustableInjectorNode withExpect(UnaryOperator<Integer> expect) {
        this.setExpect(expect.apply(this.getExpect()));
        return this;
    }

    public int getAllow() {
        return this.<Integer>get("allow").orElse(-1);
    }

    public void setAllow(int allow) {
        this.set("allow", allow);
    }

    public AdjustableInjectorNode withAllow(UnaryOperator<Integer> allow) {
        this.setAllow(allow.apply(this.getAllow()));
        return this;
    }

    public String getConstraints() {
        return this.<String>get("constraints").orElse("");
    }

    public void setConstraints(String constraints) {
        this.set("constraints", constraints);
    }

    public AdjustableInjectorNode withConstraints(UnaryOperator<String> constraints) {
        this.setConstraints(constraints.apply(this.getConstraints()));
        return this;
    }
}
