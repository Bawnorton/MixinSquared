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

package com.bawnorton.mixinsquared.selector;

import com.bawnorton.mixinsquared.TargetHandler;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.selectors.*;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.asm.IAnnotatedElement;
import org.spongepowered.asm.util.asm.MethodNodeEx;

@ITargetSelectorDynamic.SelectorId("Handler")
public final class DynamicSelectorHandler implements ITargetSelectorDynamic {
    private final String mixinName;

    private final String name;

    private final String prefix;

    private final boolean print;

    private final PrettyPrinter printer = new PrettyPrinter();

    public DynamicSelectorHandler(String mixinName, String name, String prefix, boolean print) {
        this.mixinName = mixinName;
        this.name = name;
        this.prefix = prefix == null ? null : prefix.isEmpty() ? null : prefix;
        this.print = print;
    }

    @SuppressWarnings("unused") // invoked reflectively
    public static DynamicSelectorHandler parse(String input, ISelectorContext context) {
        AnnotationNode annotationNode;

        if (context.getMethod() instanceof MethodNode) {
            MethodNode method = (MethodNode) context.getMethod();
            annotationNode = Annotations.getVisible(method, TargetHandler.class);
        } else if (context.getMethod() instanceof IAnnotatedElement) {
            IAnnotatedElement element = (IAnnotatedElement) context.getMethod();
            annotationNode = element.getAnnotation(TargetHandler.class).getValue();
        } else {
            throw new AssertionError("Could not get annotation for method");
        }
        return new DynamicSelectorHandler(Annotations.getValue(annotationNode, "mixin"), Annotations.getValue(annotationNode, "name"), Annotations.getValue(annotationNode, "prefix", ""), Annotations.getValue(annotationNode, "print", Boolean.FALSE));
    }

    @Override
    public ITargetSelector next() {
        return null;
    }

    @Override
    public ITargetSelector configure(Configure request, String... args) {
        return this;
    }

    @Override
    public ITargetSelector validate() throws InvalidSelectorException {
        return this;
    }

    @Override
    public ITargetSelector attach(ISelectorContext context) throws InvalidSelectorException {
        if (context.getMixin().getClassName().equals(mixinName)) {
            throw new InvalidSelectorException("Dynamic selector targets self!");
        }
        if (print) {
            printer.kvWidth(20)
                    .kv("Mixin", mixinName)
                    .kv("Name", name)
                    .kv("Prefix", prefix)
                    .add("%100s %-15s %-50s %s", "MIXIN", "PREFIX", "ORIGINAL NAME", "CANDIDATE")
                    .print(System.err);
        }
        return this;
    }

    @Override
    public int getMinMatchCount() {
        return 0;
    }

    @Override
    public int getMaxMatchCount() {
        return 1;
    }

    @Override
    public <TNode> MatchResult match(ElementNode<TNode> node) {
        MethodNode method = node.getMethod();
        AnnotationNode annotation = Annotations.getVisible(method, MixinMerged.class);
        if (annotation == null) return MatchResult.NONE;
        if (!(method instanceof MethodNodeEx)) return MatchResult.NONE;

        MethodNodeEx methodNodeEx = (MethodNodeEx) method;
        String targetMixinName = Annotations.getValue(annotation, "mixin");
        MatchResult result = matchInternal(targetMixinName, methodNodeEx);
        if (print)
            System.err.printf("/* %100s %-15s %-50s %9s */\n", targetMixinName, method.name.split("\\$")[0], methodNodeEx.getOriginalName(), result.isExactMatch() ? "YES" : "-");
        return result;
    }

    private MatchResult matchInternal(String targetMixinName, MethodNodeEx method) {
        if (!mixinName.equals(targetMixinName)) return MatchResult.NONE;
        if (prefix != null) {
            String methodPrefix = method.name.split("\\$")[0];
            if (!methodPrefix.equalsIgnoreCase(prefix)) return MatchResult.NONE;
        }
        if (method.getOriginalName().equals(name)) {
            return MatchResult.EXACT_MATCH;
        }
        if (method.getOriginalName().equalsIgnoreCase(name)) {
            return MatchResult.MATCH;
        }
        return MatchResult.NONE;
    }

    @Override
    public String toString() {
        return "@MixinSquared:Handler[" + "mixin='" + mixinName + '\'' + ", name='" + name + '\'' + ", prefix='" + prefix + '\'' + ']';
    }
}