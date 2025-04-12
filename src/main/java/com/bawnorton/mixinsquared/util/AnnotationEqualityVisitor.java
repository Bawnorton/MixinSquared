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
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@ApiStatus.Internal
public final class AnnotationEqualityVisitor extends AnnotationVisitor {
    private final AnnotationNode other;
    private boolean isEqual = true;
    private int index = 0;

    public AnnotationEqualityVisitor(AnnotationNode other) {
        super(327680);
        this.other = other;
    }

    public boolean isEqual() {
        return isEqual;
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
        if (!(expectedValue instanceof AnnotationNode)) {
            isEqual = false;
            return null;
        }

        return new AnnotationEqualityVisitor((AnnotationNode) expectedValue);
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
                if (arrayIndex >= expectedList.size()) {
                    isEqual = false;
                    return;
                }
                Object expectedElement = expectedList.get(arrayIndex++);
                if (!deepEquals(value, expectedElement)) {
                    isEqual = false;
                }
            }

            @Override
            public void visitEnum(String name, String desc, String value) {
                if (!isEqual) return;
                if (arrayIndex >= expectedList.size()) {
                    isEqual = false;
                    return;
                }
                Object expectedElement = expectedList.get(arrayIndex++);
                compareEnum(desc, value, expectedElement);
            }

            @Override
            public AnnotationVisitor visitAnnotation(String name, String desc) {
                if (!isEqual) return null;
                if (arrayIndex >= expectedList.size()) {
                    isEqual = false;
                    return null;
                }
                Object expectedElement = expectedList.get(arrayIndex++);
                if (!(expectedElement instanceof AnnotationNode)) {
                    isEqual = false;
                    return null;
                }
                return new AnnotationEqualityVisitor((AnnotationNode) expectedElement);
            }

            @Override
            public void visitEnd() {
                if (arrayIndex != expectedList.size()) {
                    isEqual = false;
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
        if(other.values == null || index >= other.values.size()) {
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean deepEquals(Object o1, Object o2) {
        if (o1 == o2) return true;
        if (o1 == null || o2 == null) return false;
        if (o1.getClass().isArray() && o2.getClass().isArray()) {
            if (o1 instanceof Object[] && o2 instanceof Object[]) {
                return Objects.deepEquals(o1, o2);
            } else if (o1 instanceof byte[] && o2 instanceof byte[]) {
                return Arrays.equals((byte[]) o1, (byte[]) o2);
            } else if (o1 instanceof short[] && o2 instanceof short[]) {
                return Arrays.equals((short[]) o1, (short[]) o2);
            } else if (o1 instanceof int[] && o2 instanceof int[]) {
                return Arrays.equals((int[]) o1, (int[]) o2);
            } else if (o1 instanceof long[] && o2 instanceof long[]) {
                return Arrays.equals((long[]) o1, (long[]) o2);
            } else if (o1 instanceof char[] && o2 instanceof char[]) {
                return Arrays.equals((char[]) o1, (char[]) o2);
            } else if (o1 instanceof float[] && o2 instanceof float[]) {
                return Arrays.equals((float[]) o1, (float[]) o2);
            } else if (o1 instanceof double[] && o2 instanceof double[]) {
                return Arrays.equals((double[]) o1, (double[]) o2);
            } else if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) o1, (boolean[]) o2);
            } else {
                return false;
            }
        }
        return o1.equals(o2);
    }
}