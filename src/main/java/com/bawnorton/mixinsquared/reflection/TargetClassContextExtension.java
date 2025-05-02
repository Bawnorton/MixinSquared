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

package com.bawnorton.mixinsquared.reflection;

import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import java.util.Optional;
import java.util.SortedSet;
import java.util.function.Consumer;

@ApiStatus.Internal
public final class TargetClassContextExtension {
    private final ITargetClassContext reference;

    private final FieldReference<SortedSet<?>> mixinsField;

    public TargetClassContextExtension(ITargetClassContext reference) {
        this.reference = reference;
        mixinsField = new FieldReference<>(reference.getClass(), "mixins");
    }

    public static void tryAs(ITargetClassContext reference, Consumer<TargetClassContextExtension> consumer) {
        if (reference.getClass().getName().equals("org.spongepowered.asm.mixin.transformer.TargetClassContext")) {
            consumer.accept(new TargetClassContextExtension(reference));
        }
    }

    @SuppressWarnings("unchecked")
    public SortedSet<IMixinInfo> getMixins() {
        return (SortedSet<IMixinInfo>) mixinsField.get(this.reference);
    }
}
