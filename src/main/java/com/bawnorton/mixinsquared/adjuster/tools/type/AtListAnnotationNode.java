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

package com.bawnorton.mixinsquared.adjuster.tools.type;

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAtNode;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.AnnotationNode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public interface AtListAnnotationNode extends RemappableAnnotationNode {
    default List<AdjustableAtNode> getAt() {
        return this.<List<AnnotationNode>>get("at")
                   .map(nodes -> {
                       List<AdjustableAtNode> atNodes = AdjustableAnnotationNode.fromList(nodes, AdjustableAtNode::new);
                       atNodes.forEach(at -> at.setRemapper(this.getRemapper()));
                       return atNodes;
                   })
                   .orElse(new ArrayList<>());
    }

    default void setAt(List<AdjustableAtNode> at) {
        this.set("at", at);
    }

    default AtListAnnotationNode withAt(UnaryOperator<List<AdjustableAtNode>> at) {
        this.setAt(at.apply(this.getAt()));
        return this;
    }

    @Override
    @ApiStatus.Internal
    default void applyRefmap(UnaryOperator<String> refmapApplicator) {
        this.withAt(ats -> {
            for (AdjustableAtNode at : ats) {
                at.applyRefmap(refmapApplicator);
            }
            return ats;
        });
    }
}
