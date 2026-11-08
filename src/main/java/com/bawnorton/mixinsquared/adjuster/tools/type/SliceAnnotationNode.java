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

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableSliceNode;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public interface SliceAnnotationNode extends RemappableAnnotationNode {
	/**
	 * @apiNote If using fabric mixin >=0.17.4 prefer {@link SliceAnnotationNode#getSlices()}
	 */
	default AdjustableSliceNode getSlice() {
		List<AdjustableSliceNode> slices = this.getSlices();
		return slices.isEmpty() ? AdjustableSliceNode.defaultNode() : slices.get(0);
	}

	/**
	 * @apiNote If using fabric mixin >=0.17.4 prefer {@link SliceAnnotationNode#setSlices(List)}
	 */
	default void setSlice(AdjustableSliceNode slice) {
		List<AdjustableSliceNode> slices = this.getSlices();
		if (slices.isEmpty()) slices.add(slice);
		else slices.set(0, slice);
		this.setSlices(slices);
	}

	default SliceAnnotationNode withSlice(UnaryOperator<AdjustableSliceNode> slice) {
		this.setSlice(slice.apply(this.getSlice()));
		return this;
	}

	/**
	 * @apiNote If using anything other than fabric mixin >=0.17.4 prefer {@link SliceAnnotationNode#getSlice()}
	 */
	default List<AdjustableSliceNode> getSlices() {
		List<AdjustableSliceNode> slices = new ArrayList<>();
		for (AnnotationNode node : AnnotationTargetValues.get(this, "slice")) {
			AdjustableSliceNode slice = new AdjustableSliceNode(node);
			slice.withFrom(from -> {
				from.setRemapper(this.getRemapper());
				return from;
			}).withTo(to -> {
				to.setRemapper(this.getRemapper());
				return to;
			});
			slices.add(slice);
		}
		return slices;
	}

	/**
	 * @apiNote If using anything other than fabric mixin >=0.17.4 prefer {@link SliceAnnotationNode#setSlice(AdjustableSliceNode)}
	 */
	default void setSlices(List<AdjustableSliceNode> slices) {
		AnnotationTargetValues.set(this, "slice", slices);
	}

	default SliceAnnotationNode withSlices(UnaryOperator<List<AdjustableSliceNode>> slices) {
		this.setSlices(slices.apply(this.getSlices()));
		return this;
	}

	@Override
	@ApiStatus.Internal
	default void applyRefmap(UnaryOperator<String> refmapApplicator) {
		this.withSlices(slices -> {
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
}
