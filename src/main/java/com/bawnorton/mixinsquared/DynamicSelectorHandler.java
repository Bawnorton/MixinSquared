package com.bawnorton.mixinsquared;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.selectors.*;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;

@ITargetSelectorDynamic.SelectorId("Handler")
public class DynamicSelectorHandler implements ITargetSelectorDynamic {
    private final String mixinName;

    private final String name;

    private final String prefix;

    public DynamicSelectorHandler(String mixinName, String name, String prefix) {
        this.mixinName = mixinName;
        this.name = name;
        this.prefix = prefix;
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
        return this;
    }

    @Override
    public int getMinMatchCount() {
        return 1;
    }

    @Override
    public int getMaxMatchCount() {
        return 1;
    }

    public static DynamicSelectorHandler parse(String input, ISelectorContext context) {
        if (context.getMethod() instanceof MethodNode) {
            MethodNode method = (MethodNode) context.getMethod();
            AnnotationNode annotationNode = Annotations.getVisible(method, TargetHandler.class);
            return new DynamicSelectorHandler(
                    Annotations.getValue(annotationNode, "mixin"),
                    Annotations.getValue(annotationNode, "name"),
                    Annotations.getValue(annotationNode, "prefix", "")
            );
        }
        throw new AssertionError();
    }

    @Override
    public <TNode> MatchResult match(ElementNode<TNode> node) {
        MethodNode method = node.getMethod();
        AnnotationNode annotation = Annotations.getVisible(method, MixinMerged.class);
        if (annotation == null) return MatchResult.NONE;
        if (!mixinName.equals(Annotations.getValue(annotation, "mixin"))) return MatchResult.NONE;
        if (!prefix.isEmpty() && !method.name.startsWith(prefix)) return MatchResult.NONE;

        boolean caseMatch = true;
        int start = method.name.indexOf(this.name);
        if (start == -1) {
            start = method.name.toLowerCase().indexOf(this.name.toLowerCase());
            caseMatch = false;
        }
        if (start == -1) return MatchResult.NONE;

        String uid = method.name.substring(start - 8, start);
        if (!uid.matches("\\$[a-z]{3}[0-9]{3}\\$")) return MatchResult.NONE;

        return caseMatch ? MatchResult.EXACT_MATCH : MatchResult.MATCH;
    }
}