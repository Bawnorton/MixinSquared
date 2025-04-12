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

package com.bawnorton.mixinsquared.adjuster.tools.type;

import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.injection.At;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface RemappableAnnotationNode extends MutableAnnotationNode {
    default boolean getRemap() {
        return this.<Boolean>get("remap").orElse(false);
    }

    default void setRemap(boolean remap) {
        this.set("remap", remap);
    }

    default RemappableAnnotationNode withRemap(UnaryOperator<Boolean> remap) {
        this.setRemap(remap.apply(this.getRemap()));
        return this;
    }

    /**
     * @see #applyRefmap()
     * @param refmapApplicator The function to apply to the refmap keys in the annotation.
     */
    @ApiStatus.Internal
    void applyRefmap(UnaryOperator<String> refmapApplicator);

    @ApiStatus.Internal
    void setRemapper(Consumer<RemappableAnnotationNode> remapper);

    @ApiStatus.Internal
    Consumer<RemappableAnnotationNode> getRemapper();

    /**
     * Since the Annotation Adjuster interacts with what Mixin uses as keys for the refmap (i.e {@link At#target()}) you may want to
     * remap the remappable parts of the annotation first before making your changes as once a change is made to a refmap key, the remapped
     * value will not be found, causing the mixin to fail to find its target.
     * <br/>
     * <br/>
     * By using this method, the refmap will be applied earlier in the process, allowing you to make changes to the remapped
     * values.
     * <br/>
     * <br/>
     * Example Usage:
     * <pre>
     * {@code
     * // compare against unobfuscated name
     * if(injectAnnotatioNode.getMethod().contains("someMethod(II)Z")) {
     *       // remap to obfuscated name: someMethod(II)Z -> obfuscated_name(II)Z
     *       injectAnnotationNode.applyRefmap();
     *       injectAnnotationNode.withMethod(methods -> {
     *          // would cause targetting to fail if applyRefmap was not called
     *          methods.get(0).replace("II)Z", "IIF)Z");
     *          return methods;
     *       }
     * }
     * }
     * </pre>
     * @apiNote This will always remap regardless of whether you are in a dev environment or not,
     * as long as there is a refmap key value pair for the remappable elmenets they will be remapped
     */
    @ApiStatus.AvailableSince("0.3.0-beta.1")
    default void applyRefmap() {
        Consumer<RemappableAnnotationNode> remapper = getRemapper();
        if (remapper != null) {
            remapper.accept(this);
        }
    }
}
