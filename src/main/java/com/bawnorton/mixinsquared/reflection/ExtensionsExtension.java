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
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry;
import java.util.List;
import java.util.function.Consumer;

@ApiStatus.Internal
public final class ExtensionsExtension {
    private final Extensions reference;

    private final FieldReference<List<IExtension>> extensionsField;
    private final FieldReference<List<IExtension>> activeExtensionsField;

    public ExtensionsExtension(Extensions reference) {
        this.reference = reference;
        extensionsField = new FieldReference<>(reference.getClass(), "extensions");
        activeExtensionsField = new FieldReference<>(reference.getClass(), "activeExtensions");
    }

    public static void tryAs(IExtensionRegistry reference, Consumer<ExtensionsExtension> consumer) {
        if (reference instanceof Extensions) {
            consumer.accept(new ExtensionsExtension((Extensions) reference));
        }
    }

    public List<IExtension> getExtensions() {
        return extensionsField.get(this.reference);
    }

    public List<IExtension> getActiveExtensions() {
        return reference.getActiveExtensions();
    }

    public void setActiveExtensions(List<IExtension> extensions) {
        activeExtensionsField.set(this.reference, extensions);
    }
}
