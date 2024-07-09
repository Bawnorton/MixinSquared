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
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AdjustableNextNode extends AdjustableAnnotationNode {
    public AdjustableNextNode(AnnotationNode node) {
        super(node);
    }

    public static AdjustableNextNode defaultNode() {
        AnnotationNode node = new AnnotationNode(KnownAnnotations.NEXT.desc());
        return new AdjustableNextNode(node);
    }

    public String getName() {
        return this.<String>get("name").orElse("");
    }

    public void setName(String name) {
        this.set("name", name);
    }

    public AdjustableNextNode withName(UnaryOperator<String> name) {
        this.setName(name.apply(this.getName()));
        return this;
    }

    public Type getRet() {
        return this.<Type>get("ret").orElse(Type.VOID_TYPE);
    }

    public void setRet(Type ret) {
        this.set("ret", ret);
    }

    public AdjustableNextNode withRet(UnaryOperator<Type> ret) {
        this.setRet(ret.apply(this.getRet()));
        return this;
    }

    public List<Type> getArgs() {
        return this.<List<Type>>get("args").orElse(new ArrayList<>());
    }

    public void setArgs(List<Type> args) {
        this.set("args", args);
    }

    public AdjustableNextNode withArgs(UnaryOperator<List<Type>> args) {
        this.setArgs(args.apply(this.getArgs()));
        return this;
    }

    public int getMin() {
        return this.<Integer>get("min").orElse(0);
    }

    public void setMin(int min) {
        this.set("min", min);
    }

    public AdjustableNextNode withMin(UnaryOperator<Integer> min) {
        this.setMin(min.apply(this.getMin()));
        return this;
    }

    public int getMax() {
        return this.<Integer>get("max").orElse(Integer.MAX_VALUE);
    }

    public void setMax(int max) {
        this.set("max", max);
    }

    public AdjustableNextNode withMax(UnaryOperator<Integer> max) {
        this.setMax(max.apply(this.getMax()));
        return this;
    }
}
