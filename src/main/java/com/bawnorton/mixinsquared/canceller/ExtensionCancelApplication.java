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

package com.bawnorton.mixinsquared.canceller;

import com.bawnorton.mixinsquared.reflection.TargetClassContextExtension;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.service.MixinService;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public final class ExtensionCancelApplication implements IExtension {
    private static final ILogger LOGGER = MixinService.getService().getLogger("mixinsquared-canceller");

    @Override
    public boolean checkActive(MixinEnvironment environment) {
        return true;
    }

    @Override
    public void preApply(ITargetClassContext context) {
        TargetClassContextExtension.tryAs(context).ifPresent(contextExtension -> {
            SortedSet<IMixinInfo> mixins = contextExtension.getMixins();
            mixins.removeIf(mixin -> {
                List<String> targetClassNames = mixin.getTargetClasses().stream().map(s -> s.replaceAll("/", ".")).collect(Collectors.toList());
                boolean shouldCancel = MixinCancellerRegistrar.shouldCancel(targetClassNames, mixin.getClassName(), canceller -> LOGGER.debug("Canceller {} cancelled mixin {}", canceller, mixin.getClassName()));
                if (shouldCancel) {
                    LOGGER.warn("Cancelled mixin {}. Check debug logs for more information.", mixin.getClassName());
                }
                return shouldCancel;
            });
        });
    }

    @Override
    public void postApply(ITargetClassContext context) {
    }

    @Override
    public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {
    }
}
