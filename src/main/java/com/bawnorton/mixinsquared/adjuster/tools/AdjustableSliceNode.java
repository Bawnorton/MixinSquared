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
import java.util.function.UnaryOperator;

public class AdjustableSliceNode extends AdjustableAnnotationNode {
    public AdjustableSliceNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableSliceNode defaultNode() {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.SLICE.desc());
        return new AdjustableSliceNode(node);
    }

    public String getId() {
        return this.<String>get("id").orElse("");
    }

    public void setId(String id) {
        this.set("id", id);
    }

    public AdjustableSliceNode withId(UnaryOperator<String> id) {
        this.setId(id.apply(this.getId()));
        return this;
    }

    public AdjustableAtNode getFrom() {
        return this.<AnnotationNode>get("from")
                   .map(AdjustableAtNode::new)
                   .orElse(AdjustableAtNode.InjectionPoint.HEAD.toNode());
    }

    public void setFrom(AdjustableAtNode from) {
        this.set("from", from);
    }

    public AdjustableSliceNode withFrom(UnaryOperator<AdjustableAtNode> from) {
        this.setFrom(from.apply(this.getFrom()));
        return this;
    }

    public AdjustableAtNode getTo() {
        return this.<AnnotationNode>get("to")
                   .map(AdjustableAtNode::new)
                   .orElse(AdjustableAtNode.InjectionPoint.TAIL.toNode());
    }

    public void setTo(AdjustableAtNode to) {
        this.set("to", to);
    }

    public AdjustableSliceNode withTo(UnaryOperator<AdjustableAtNode> to) {
        this.setTo(to.apply(this.getTo()));
        return this;
    }
}
