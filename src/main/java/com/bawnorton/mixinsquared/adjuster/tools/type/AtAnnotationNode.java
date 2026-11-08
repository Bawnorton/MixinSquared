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

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAtNode;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public interface AtAnnotationNode extends RemappableAnnotationNode {
	/**
	 * @apiNote If using fabric mixin >=0.17.4 prefer {@link AtAnnotationNode#getAts()}
	 */
	default AdjustableAtNode getAt() {
		List<AdjustableAtNode> ats = this.getAts();
		return ats.isEmpty() ? null : ats.get(0);
	}

	/**
	 * @apiNote If using fabric mixin >=0.17.4 prefer {@link AtAnnotationNode#setAts(List)}
	 */
	default void setAt(AdjustableAtNode at) {
		List<AdjustableAtNode> ats = this.getAts();
		if (ats.isEmpty()) {
            ats.add(at);
        } else {
            ats.set(0, at);
        }
		this.setAts(ats);
	}

	default AtAnnotationNode withAt(UnaryOperator<AdjustableAtNode> at) {
		this.setAt(at.apply(this.getAt()));
		return this;
	}

	/**
	 * @apiNote If using anything other than fabric mixin >=0.17.4 prefer {@link AtAnnotationNode#getAt()}
	 */
	default List<AdjustableAtNode> getAts() {
		List<AdjustableAtNode> ats = new ArrayList<>();
		for (AnnotationNode node : AnnotationTargetValues.get(this, "at")) {
			AdjustableAtNode at = new AdjustableAtNode(node);
			at.setRemapper(this.getRemapper());
			ats.add(at);
		}
		return ats;
	}

	/**
	 * @apiNote If using anything other than fabric mixin >=0.17.4 prefer {@link AtAnnotationNode#setAt(AdjustableAtNode)}
	 */
	default void setAts(List<AdjustableAtNode> ats) {
		AnnotationTargetValues.set(this, "at", ats);
	}

	default AtAnnotationNode withAts(UnaryOperator<List<AdjustableAtNode>> ats) {
		this.setAts(ats.apply(this.getAts()));
		return this;
	}

	@Override
	@ApiStatus.Internal
	default void applyRefmap(UnaryOperator<String> refmapApplicator) {
		this.withAts(ats -> {
			for (AdjustableAtNode at : ats) {
				at.applyRefmap(refmapApplicator);
			}
			return ats;
		});
	}
}
