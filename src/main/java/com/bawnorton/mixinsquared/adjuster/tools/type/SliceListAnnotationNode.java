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
import com.bawnorton.mixinsquared.adjuster.tools.AdjustableSliceNode;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.AnnotationNode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface SliceListAnnotationNode extends RemappableAnnotationNode {
    default List<AdjustableSliceNode> getSlice() {
        return this.<List<AnnotationNode>>get("slice")
                   .map(nodes -> AdjustableAnnotationNode.fromList(nodes, AdjustableSliceNode::new))
                   .orElse(new ArrayList<>());
    }

    default void setSlice(List<AdjustableSliceNode> slice) {
        this.set("slice", slice);
    }

    default SliceListAnnotationNode withSlice(UnaryOperator<List<AdjustableSliceNode>> slice) {
        this.setSlice(slice.apply(this.getSlice()));
        return this;
    }

    @Override
    @ApiStatus.Internal
    default void applyRefmap(UnaryOperator<String> refmapApplicator) {
        this.withSlice(slices -> {
            for (AdjustableSliceNode slice : slices) {
                slice.withFrom(from -> {
                    from.applyRefmap(refmapApplicator);
                    return from;
                }).withTo(to -> {
                    to.applyRefmap(refmapApplicator);
                    return to;
                });
            }
            return slices;
        });
    }

    @Override
    @ApiStatus.Internal
    default void setRemapper(Consumer<RemappableAnnotationNode> remapper) {
        getSlice().forEach(slice -> {
            slice.getFrom().setRemapper(remapper);
            slice.getTo().setRemapper(remapper);
        });
    }
}
