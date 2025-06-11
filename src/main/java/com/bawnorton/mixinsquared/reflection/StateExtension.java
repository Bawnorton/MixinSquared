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

package com.bawnorton.mixinsquared.reflection;

import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.ClassNode;
import java.util.function.Consumer;

@ApiStatus.Internal
public final class StateExtension {
    private final Object reference;

    private final FieldReference<ClassNode> classNodeField;

    private StateExtension(Object reference) {
        this.reference = reference;
        this.classNodeField = new FieldReference<>(reference.getClass(), "classNode");
    }

    public static void tryAs(Object object, Consumer<StateExtension> consumer) {
        if (object.getClass().getName().equals("org.spongepowered.asm.mixin.transformer.MixinInfo$State")) {
            consumer.accept(new StateExtension(object));
        }
    }

    public void setClassNode(ClassNode classNode) {
        classNodeField.set(reference, classNode);
    }
}
