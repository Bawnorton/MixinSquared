package com.bawnorton.mixinsquared;

import com.bawnorton.mixinsquared.tools.MixinAnnotationReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MixinAnnotationReaderTest {
    @Test
    void usesDefaultMixinAnnotationValuesWhenUnset() {
        ClassNode classNode = mixinClassNode(null, null, null, null);

        Assertions.assertEquals(Collections.emptyList(), MixinAnnotationReader.getValue(classNode));
        assertEquals(Collections.emptyList(), MixinAnnotationReader.getTargets(classNode));
        assertEquals(1000, MixinAnnotationReader.getPriority(classNode));
        assertTrue(MixinAnnotationReader.getRemap(classNode));
    }

    @Test
    void readsExplicitMixinAnnotationValues() {
        List<Type> value = Arrays.asList(
                Type.getType("Lcom/example/Foo;"),
                Type.getType("Lcom/example/Bar;")
        );
        List<String> targets = Arrays.asList("com.example.TargetA", "com.example.TargetB");

        ClassNode classNode = mixinClassNode(value, targets, 2000, false);

        assertEquals(value, MixinAnnotationReader.getValue(classNode));
        assertEquals(targets, MixinAnnotationReader.getTargets(classNode));
        assertEquals(2000, MixinAnnotationReader.getPriority(classNode));
        assertFalse(MixinAnnotationReader.getRemap(classNode));
    }

    private static ClassNode mixinClassNode(List<Type> value, List<String> targets, Integer priority, Boolean remap) {
        AnnotationNode mixin = new AnnotationNode(Type.getDescriptor(Mixin.class));
        mixin.values = new ArrayList<>();

        if (value != null) {
            mixin.values.add("value");
            mixin.values.add(value);
        }
        if (targets != null) {
            mixin.values.add("targets");
            mixin.values.add(targets);
        }
        if (priority != null) {
            mixin.values.add("priority");
            mixin.values.add(priority);
        }
        if (remap != null) {
            mixin.values.add("remap");
            mixin.values.add(remap);
        }

        ClassNode classNode = new ClassNode();
        classNode.invisibleAnnotations = new ArrayList<>();
        classNode.invisibleAnnotations.add(mixin);
        return classNode;
    }
}

