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

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.Constant;
import java.util.function.UnaryOperator;

public class AdjustableConstantNode extends AdjustableAnnotationNode {
    public AdjustableConstantNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableConstantNode defaultNode() {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.CONSTANT.desc());
        return new AdjustableConstantNode(node);
    }

    public boolean getNullValue() {
        return this.<Boolean>get("nullValue").orElse(false);
    }

    public void setNullValue(boolean nullValue) {
        this.set("nullValue", nullValue);
    }

    public AdjustableConstantNode withNullValue(UnaryOperator<Boolean> nullValue) {
        this.setNullValue(nullValue.apply(this.getNullValue()));
        return this;
    }

    public int getIntValue() {
        return this.<Integer>get("intValue").orElse(0);
    }

    public void setIntValue(int intValue) {
        this.set("intValue", intValue);
    }

    public AdjustableConstantNode withIntValue(UnaryOperator<Integer> intValue) {
        this.setIntValue(intValue.apply(this.getIntValue()));
        return this;
    }

    public float getFloatValue() {
        return this.<Float>get("floatValue").orElse(0.0f);
    }

    public void setFloatValue(float floatValue) {
        this.set("floatValue", floatValue);
    }

    public AdjustableConstantNode withFloatValue(UnaryOperator<Float> floatValue) {
        this.setFloatValue(floatValue.apply(this.getFloatValue()));
        return this;
    }

    public long getLongValue() {
        return this.<Long>get("longValue").orElse(0L);
    }

    public void setLongValue(long longValue) {
        this.set("longValue", longValue);
    }

    public AdjustableConstantNode withLongValue(UnaryOperator<Long> longValue) {
        this.setLongValue(longValue.apply(this.getLongValue()));
        return this;
    }

    public double getDoubleValue() {
        return this.<Double>get("doubleValue").orElse(0.0);
    }

    public void setDoubleValue(double doubleValue) {
        this.set("doubleValue", doubleValue);
    }

    public AdjustableConstantNode withDoubleValue(UnaryOperator<Double> doubleValue) {
        this.setDoubleValue(doubleValue.apply(this.getDoubleValue()));
        return this;
    }

    public String getStringValue() {
        return this.<String>get("stringValue").orElse("");
    }

    public void setStringValue(String stringValue) {
        this.set("stringValue", stringValue);
    }

    public AdjustableConstantNode withStringValue(UnaryOperator<String> stringValue) {
        this.setStringValue(stringValue.apply(this.getStringValue()));
        return this;
    }

    public Type getClassValue() {
        return this.<Type>get("classValue").orElse(Type.getType(Object.class));
    }

    public void setClassValue(Type classValue) {
        this.set("classValue", classValue);
    }

    public AdjustableConstantNode withClassValue(UnaryOperator<Type> classValue) {
        this.setClassValue(classValue.apply(this.getClassValue()));
        return this;
    }

    public int getOrdinal() {
        return this.<Integer>get("ordinal").orElse(-1);
    }

    public void setOrdinal(int ordinal) {
        this.set("ordinal", ordinal);
    }

    public AdjustableConstantNode withOrdinal(UnaryOperator<Integer> ordinal) {
        this.setOrdinal(ordinal.apply(this.getOrdinal()));
        return this;
    }

    public String getSlice() {
        return this.<String>get("slice").orElse("");
    }

    public void setSlice(String slice) {
        this.set("slice", slice);
    }

    public AdjustableConstantNode withSlice(UnaryOperator<String> slice) {
        this.setSlice(slice.apply(this.getSlice()));
        return this;
    }

    public Constant.Condition[] getExpandZeroConditions() {
        return this.<Constant.Condition[]>get("expandZeroConditions").orElse(new Constant.Condition[0]);
    }

    public void setExpandZeroConditions(Constant.Condition[] expandZeroConditions) {
        this.set("expandZeroConditions", expandZeroConditions);
    }

    public AdjustableConstantNode withExpandZeroConditions(UnaryOperator<Constant.Condition[]> expandZeroConditions) {
        this.setExpandZeroConditions(expandZeroConditions.apply(this.getExpandZeroConditions()));
        return this;
    }

    public boolean getLog() {
        return this.<Boolean>get("log").orElse(false);
    }

    public void setLog(boolean log) {
        this.set("log", log);
    }

    public AdjustableConstantNode withLog(UnaryOperator<Boolean> log) {
        this.setLog(log.apply(this.getLog()));
        return this;
    }
}
