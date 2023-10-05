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

package com.bawnorton.mixinsquared;

import com.bawnorton.mixinsquared.canceller.ExtensionCancelApplication;
import com.bawnorton.mixinsquared.canceller.ExtensionRegistrar;
import com.bawnorton.mixinsquared.selector.DynamicSelectorHandler;
import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;

public final class MixinSquaredBootstrap {
    public static final String NAME = "mixinsquared";
    public static final String VERSION = "0.1.1";

    private static boolean initialized = false;

    public static void init() {
        init(true);
    }

    static void init(boolean runtime) {
        if (initialized) return;

        initialized = true;

        TargetSelector.register(DynamicSelectorHandler.class, "MixinSquared");

        if (runtime) {
            ExtensionRegistrar.register(new ExtensionCancelApplication());
        }
    }
}
