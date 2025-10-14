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

import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public interface MethodListAnnotationNode extends RemappableAnnotationNode {
	default List<String> getMethod() {
		return this.<List<String>>get("method").orElse(new ArrayList<>());
	}

	default void setMethod(List<String> method) {
		this.set("method", method);
	}

	default MethodListAnnotationNode withMethod(UnaryOperator<List<String>> method) {
		this.setMethod(method.apply(this.getMethod()));
		return this;
	}

	@Override
	@ApiStatus.Internal
	default void applyRefmap(UnaryOperator<String> refmapApplicator) {
		this.withMethod(methods -> {
			List<String> newMethods = new ArrayList<>(methods.size());
			for (String method : methods) {
				newMethods.add(refmapApplicator.apply(method));
			}
			return newMethods;
		});
	}
}
