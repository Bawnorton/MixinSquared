package com.bawnorton.mixinsquared.reflection;

import org.objectweb.asm.tree.ClassNode;
import java.util.Optional;

public final class StateExtension {
    private final Object reference;

    private final FieldReference<ClassNode> classNodeField;

    private StateExtension(Object reference) {
        this.reference = reference;
        this.classNodeField = new FieldReference<>(reference.getClass(), "classNode");
    }

    public static Optional<StateExtension> tryAs(Object object) {
        if (object.getClass().getName().equals("org.spongepowered.asm.mixin.transformer.MixinInfo$State")) {
            return Optional.of(new StateExtension(object));
        }
        return Optional.empty();
    }

    public void setClassNode(ClassNode classNode) {
        classNodeField.set(reference, classNode);
    }
}
