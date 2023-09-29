package com.bawnorton.mixinsquared.tools;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.service.IClassBytecodeProvider;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public final class MixinAnnotationReader {
    private static final IClassBytecodeProvider bytecodeProvider;
    private final AnnotationNode mixinAnnotation;

    public MixinAnnotationReader(String mixinClassName) {
        try {
            ClassNode classNode = bytecodeProvider.getClassNode(mixinClassName);
            mixinAnnotation = Annotations.getInvisible(classNode, Mixin.class);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MixinAnnotationReader(ClassNode mixinClassNode) {
        mixinAnnotation = Annotations.getInvisible(mixinClassNode, Mixin.class);
    }

    static {
         bytecodeProvider = MixinService.getService().getBytecodeProvider();
    }

    public List<Type> getValue() {
        return Annotations.getValue(mixinAnnotation, "value", Collections.emptyList());
    }

    public List<String> getTargets() {
        return Annotations.getValue(mixinAnnotation, "targets", Collections.emptyList());
    }

    public int getPriority() {
        return Annotations.getValue(mixinAnnotation, "priority", 1000);
    }

    public boolean getRemap() {
        return Annotations.getValue(mixinAnnotation, "remap", Boolean.TRUE);
    }

    public static List<Type> getValue(String mixinClassName) {
        return new MixinAnnotationReader(mixinClassName).getValue();
    }

    public static List<String> getTargets(String mixinClassName) {
        return new MixinAnnotationReader(mixinClassName).getTargets();
    }

    public static int getPriority(String mixinClassName) {
        return new MixinAnnotationReader(mixinClassName).getPriority();
    }

    public static boolean getRemap(String mixinClassName) {
        return new MixinAnnotationReader(mixinClassName).getRemap();
    }

    public static List<Type> getValue(ClassNode mixinClassNode) {
        return new MixinAnnotationReader(mixinClassNode).getValue();
    }

    public static List<String> getTargets(ClassNode mixinClassNode) {
        return new MixinAnnotationReader(mixinClassNode).getTargets();
    }

    public static int getPriority(ClassNode mixinClassNode) {
        return new MixinAnnotationReader(mixinClassNode).getPriority();
    }

    public static boolean getRemap(ClassNode mixinClassNode) {
        return new MixinAnnotationReader(mixinClassNode).getRemap();
    }
}
