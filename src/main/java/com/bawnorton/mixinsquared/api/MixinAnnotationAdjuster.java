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

package com.bawnorton.mixinsquared.api;

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.type.RemappableAnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import java.util.List;

public interface MixinAnnotationAdjuster {
    /**
     * Adjusts the annotation node for the given handler method node.
     * @param targetClassNames List of target class names in "com.example.Example" format. These are unmapped class names.
     * @param mixinClassName Mixin class name in "com.example.ExampleMixin" format.
     * @param handlerNode Method node of the handler method. Read/Write.
     * @param annotationNode Annotation node to adjust. Read/Write. Node will always be present but the annotation class may not exist at runtime.
     * @return Adjusted annotation node. Return null to remove the annotation.
     * @see RemappableAnnotationNode#applyRefmap()
     */
    AdjustableAnnotationNode adjust(List<String> targetClassNames, String mixinClassName, MethodNode handlerNode, AdjustableAnnotationNode annotationNode);
}
