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
import com.bawnorton.mixinsquared.reflection.AnnotatedMixinExtension;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.selectors.ElementNode;
import org.spongepowered.asm.mixin.injection.selectors.ISelectorContext;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorDynamic;
import org.spongepowered.asm.mixin.injection.selectors.InvalidSelectorException;
import org.spongepowered.asm.mixin.injection.selectors.MatchResult;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.asm.IAnnotatedElement;
import org.spongepowered.asm.util.asm.IAnnotationHandle;
import org.spongepowered.asm.util.asm.MethodNodeEx;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import java.util.List;

@ITargetSelectorDynamic.SelectorId("Handler")
public final class DynamicSelectorHandler implements ITargetSelectorDynamic {
    private final String mixinName;

    private final String name;

    private final String desc;

    private final String prefix;

    private final boolean print;

    public DynamicSelectorHandler(String mixinName, MemberInfo memberInfo, String prefix, boolean print) {
        this.mixinName = mixinName;
        this.name = memberInfo.getName();
        this.desc = memberInfo.getDesc();
        this.prefix = prefix == null ? null : prefix.isEmpty() ? null : prefix;
        this.print = print;
    }

    @SuppressWarnings("unused") // invoked reflectively
    public static DynamicSelectorHandler parse(String input, ISelectorContext context) {
        AnnotationNode annotationNode;

        if (context.getMethod() instanceof MethodNode) {
            MethodNode method = (MethodNode) context.getMethod();
            annotationNode = Annotations.getVisible(method, TargetHandler.class);
            return parseRuntime(annotationNode, context);
        } else if (context.getMethod() instanceof IAnnotatedElement) {
            IAnnotatedElement element = (IAnnotatedElement) context.getMethod();
            return parseCompileTime(element, context);
        } else {
            throw new AssertionError("Could not get annotation for method");
        }
    }

    private static DynamicSelectorHandler parseCompileTime(IAnnotatedElement element, ISelectorContext context) {
        IAnnotationHandle annotation = element.getAnnotation(TargetHandler.class);
        String name = annotation.getValue("name");
        MemberInfo memberInfo = MemberInfo.parse(name, context);
        String mixin = annotation.getValue("mixin");

        if(memberInfo.getDesc() != null) {
            AnnotatedMixinExtension.tryAs(context.getMixin(), extension -> {
                IObfuscationManager obfManager = extension.getObfuscationManager();
                ITypeHandleProvider typeProvider = extension.getTypeProvider();
                TypeHandle typeHandle = typeProvider.getTypeHandle(mixin);
                MappingMethod targetMethod = typeHandle.getMappingMethod(memberInfo.getName(), memberInfo.getDesc());
                ObfuscationData<MappingMethod> obfData = obfManager.getDataProvider().getObfMethod(targetMethod);
                IReferenceManager refManager = obfManager.getReferenceManager();
                refManager.addMethodMapping(context.getMixin().getClassRef(), name, obfData);
            });
        }

        String prefix = annotation.getValue("prefix", "");
        boolean print = annotation.getValue("print", Boolean.FALSE);
        return new DynamicSelectorHandler(mixin, memberInfo, prefix, print);
    }

    private static DynamicSelectorHandler parseRuntime(AnnotationNode node, ISelectorContext context) {
        String name = Annotations.getValue(node, "name");
        MemberInfo memberInfo = MemberInfo.parse(name, context);
        String mixin = Annotations.getValue(node, "mixin");
        String prefix = Annotations.getValue(node, "prefix", "");
        boolean print = Annotations.getValue(node, "print", Boolean.FALSE);
        return new DynamicSelectorHandler(mixin, memberInfo, prefix, print);
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
        if(!print) return this;

        PrettyPrinter printer = new PrettyPrinter();
        printer.kvWidth(20);
        printer.kv("Target Mixin", mixinName);
        printer.kv("Target Method", name);
        printer.kv("Target Desc", desc == null ? "None" : desc);
        printer.kv("Target Prefix", prefix == null ? "None" : prefix);
        printer.kv("Print", "Print is enabled, matching disabled!");
        printer.hr();

        if(!(context instanceof SpecialMethodInfo)) return this;
        SpecialMethodInfo specialMethodInfo = (SpecialMethodInfo) context;

        ClassNode classNode;
        try {
            classNode = specialMethodInfo.getTargetClassNode();
        } catch (NoSuchMethodError ignored) {
            //noinspection deprecation
            classNode = specialMethodInfo.getClassNode();
        }

        List<MethodNode> methods = classNode.methods;
        for(MethodNode method : methods) {
            if(!(method instanceof MethodNodeEx)) continue;
            MethodNodeEx methodNodeEx = (MethodNodeEx) method;

            String mixinName = methodNodeEx.getOwner().getClassName();
            String originalName = methodNodeEx.getOriginalName();
            String prefix = method.name.split("\\$")[0];
            String originalDesc = method.desc;
            printer.kv("Name", originalName + originalDesc);
            printer.kv("Prefix", prefix);
            String candidateType = "NO";
            MatchResult matchResult = matchInternal(mixinName, originalName, originalDesc, prefix);
            if(matchResult == MatchResult.EXACT_MATCH) {
                candidateType = "YES";
            } else if(matchResult == MatchResult.MATCH) {
                candidateType = "PARTIAL";
            }
            printer.kv("Candidate", candidateType);
            printer.hr('-');
        }
        printer.print(System.err);
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
        if(print) return MatchResult.NONE;

        MethodNode method = node.getMethod();
        AnnotationNode annotation = Annotations.getVisible(method, MixinMerged.class);
        if (annotation == null) return MatchResult.NONE;
        if (!(method instanceof MethodNodeEx)) return MatchResult.NONE;

        MethodNodeEx methodNodeEx = (MethodNodeEx) method;
        String targetMixinName = Annotations.getValue(annotation, "mixin");
        String originalName = methodNodeEx.getOriginalName();
        String desc = methodNodeEx.desc;
        String prefix = methodNodeEx.name.split("\\$")[0];
        return matchInternal(targetMixinName, originalName, desc, prefix);
    }

    private MatchResult matchInternal(String targetMixinName, String name, String desc, String prefix) {
        if (!mixinName.equals(targetMixinName)) return MatchResult.NONE;

        if (this.desc != null && desc != null && !this.desc.equals(desc)) return MatchResult.NONE;
        if (this.prefix != null && prefix != null && !this.prefix.equals(prefix)) return MatchResult.NONE;
        if (this.name.equals(name)) return MatchResult.EXACT_MATCH;
        if (this.name.equalsIgnoreCase(name)) return MatchResult.MATCH;
        return MatchResult.NONE;
    }

    @Override
    public String toString() {
        return "@MixinSquared:Handler[" + "mixin='" + mixinName + '\'' + ", name='" + name + '\'' + ", desc='" + desc + '\'' + ", prefix='" + prefix + '\'' + ']';
    }
}