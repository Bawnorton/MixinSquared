/*
 * MIT License
 *
 * Copyright (c) 2026-present Bawnorton
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

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class AnnotationTargetValues {
	private AnnotationTargetValues() {
	}

	static List<AnnotationNode> get(MutableAnnotationNode owner, String key) {
		Object value = owner.get(key).orElse(null);
		if (value == null) return new ArrayList<>();

		if (value instanceof AnnotationNode) {
			List<AnnotationNode> nodes = new ArrayList<>(1);
			nodes.add((AnnotationNode) value);
			return nodes;
		}

		if (value instanceof AnnotationNode[]) {
			return new ArrayList<>(Arrays.asList((AnnotationNode[]) value));
		}

		if (value instanceof List<?>) {
			List<AnnotationNode> nodes = new ArrayList<>();
			for (Object element : (List<?>) value) {
				nodes.add((AnnotationNode) element);
			}
			return nodes;
		}

		throw new IllegalStateException("Unexpected value for annotation member " + key + ": " + value.getClass());
	}

	static void set(MutableAnnotationNode owner, String key, List<? extends AnnotationNode> nodes) {
		Object oldValue = owner.get(key).orElse(null);
		boolean isArray = oldValue instanceof List<?>
				|| oldValue instanceof AnnotationNode[]
				|| oldValue == null
				&& hasArrayMember(owner, key);
		owner.set(key, isArray || nodes.size() != 1 ? new ArrayList<>(nodes) : nodes.get(0));
	}

	private static boolean hasArrayMember(MutableAnnotationNode owner, String key) {
		if (!(owner instanceof AnnotationNode)) return false;

		String className = Type.getType(((AnnotationNode) owner).desc).getClassName();
		try {
			Class<?> annotationType = Class.forName(className, false, AnnotationTargetValues.class.getClassLoader());
			return annotationType.getMethod(key).getReturnType().isArray();
		} catch (ClassNotFoundException | NoSuchMethodException exception) {
			return false;
		}
	}
}
