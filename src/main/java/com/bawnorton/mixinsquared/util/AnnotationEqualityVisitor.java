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

package com.bawnorton.mixinsquared.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

@ApiStatus.Internal
public final class AnnotationEqualityVisitor extends AnnotationVisitor {
	private static final int ASM_API = 327680;
	private static final Object ARRAY_END = new Object();

	private final AnnotationNode other;
	private boolean isEqual = true;
	private int index = 0;

	public AnnotationEqualityVisitor(AnnotationNode other) {
		super(ASM_API);
		this.other = other;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private static boolean deepEquals(Object o1, Object o2) {
		if (o1 == o2) return true;
		if (o1 == null || o2 == null) return false;
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (!o1.getClass().equals(o2.getClass())) return false;
			int length = Array.getLength(o1);
			if (length != Array.getLength(o2)) return false;
			for (int i = 0; i < length; i++) {
				if (!deepEquals(Array.get(o1, i), Array.get(o2, i))) {
					return false;
				}
			}
			return true;
		}
		return o1.equals(o2);
	}

	public boolean isEqual() {
		return isEqual;
	}

	public void visit(AnnotationNode annotationNode) {
		if (annotationNode == null) {
			isEqual = false;
			return;
		}
		if (!Objects.equals(annotationNode.desc, other.desc)) {
			isEqual = false;
			return;
		}
		annotationNode.accept(this);
	}

	@Override
	public void visit(String name, Object value) {
		if (!isEqual) return;

		Object expectedValue = getExpectedValue(name);
		if (!deepEquals(value, expectedValue)) {
			isEqual = false;
		}
	}

	@Override
	public void visitEnum(String name, String desc, String value) {
		if (!isEqual) return;

		Object expectedValue = getExpectedValue(name);
		compareEnum(desc, value, expectedValue);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String desc) {
		if (!isEqual) return null;

		Object expectedValue = getExpectedValue(name);
		return compareNestedAnnotation(desc, expectedValue);
	}

	@Override
	public AnnotationVisitor visitArray(final String name) {
		if (!isEqual) return null;

		Object expectedValue = getExpectedValue(name);
		if (!(expectedValue instanceof List)) {
			isEqual = false;
			return null;
		}

		final List<?> expectedList = (List<?>) expectedValue;

		return new AnnotationVisitor(api) {
			int arrayIndex = 0;

			@Override
			public void visit(String name, Object value) {
				if (!isEqual) return;
				Object expectedElement = getNextArrayElement();
				if (expectedElement == ARRAY_END) {
					isEqual = false;
					return;
				}
				if (!deepEquals(value, expectedElement)) {
					isEqual = false;
				}
			}

			@Override
			public void visitEnum(String name, String desc, String value) {
				if (!isEqual) return;
				Object expectedElement = getNextArrayElement();
				if (expectedElement == ARRAY_END) {
					isEqual = false;
					return;
				}
				compareEnum(desc, value, expectedElement);
			}

			@Override
			public AnnotationVisitor visitAnnotation(String name, String desc) {
				if (!isEqual) return null;
				Object expectedElement = getNextArrayElement();
				if (expectedElement == ARRAY_END) {
					isEqual = false;
					return null;
				}
				return compareNestedAnnotation(desc, expectedElement);
			}

			@Override
			public void visitEnd() {
				if (arrayIndex != expectedList.size()) {
					isEqual = false;
				}
			}

			private Object getNextArrayElement() {
				if (arrayIndex >= expectedList.size()) {
					return ARRAY_END;
				}
				return expectedList.get(arrayIndex++);
			}
		};
	}

	@Nullable
	private AnnotationVisitor compareNestedAnnotation(String desc, Object expectedElement) {
		if (!(expectedElement instanceof AnnotationNode)) {
			isEqual = false;
			return null;
		}
		AnnotationNode expectedAnnotation = (AnnotationNode) expectedElement;
		if (!Objects.equals(desc, expectedAnnotation.desc)) {
			isEqual = false;
			return null;
		}
		return createLinkedVisitor(expectedAnnotation);
	}

	private AnnotationVisitor createLinkedVisitor(AnnotationNode expectedAnnotation) {
		AnnotationEqualityVisitor childVisitor = new AnnotationEqualityVisitor(expectedAnnotation);
		return new AnnotationVisitor(api, childVisitor) {
			@Override
			public void visitEnd() {
				super.visitEnd();
				if (!childVisitor.isEqual()) {
					AnnotationEqualityVisitor.this.isEqual = false;
				}
			}
		};
	}

	private void compareEnum(String desc, String value, Object expectedElement) {
		if (!(expectedElement instanceof String[])) {
			isEqual = false;
			return;
		}
		String[] expectedEnum = (String[]) expectedElement;
		if (expectedEnum.length != 2 || !desc.equals(expectedEnum[0]) || !value.equals(expectedEnum[1])) {
			isEqual = false;
		}
	}

	@Override
	public void visitEnd() {
		if (index != (other.values == null ? 0 : other.values.size())) {
			isEqual = false;
		}
	}

	private Object getExpectedValue(String name) {
		if (other.values == null || index >= other.values.size()) {
			isEqual = false;
			return null;
		}
		Object expectedName = other.values.get(index++);
		Object expectedValue = other.values.get(index++);

		if (name != null && !name.equals(expectedName)) {
			isEqual = false;
			return null;
		}

		return expectedValue;
	}
}