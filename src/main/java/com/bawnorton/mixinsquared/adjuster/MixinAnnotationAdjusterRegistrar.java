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

package com.bawnorton.mixinsquared.adjuster;

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAnnotationNode;
import com.bawnorton.mixinsquared.api.MixinAnnotationAdjuster;
import com.bawnorton.mixinsquared.util.AnnotationEqualityVisitor;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.service.MixinService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public final class MixinAnnotationAdjusterRegistrar {
	private static final Set<MixinAnnotationAdjuster> adjusters = new HashSet<>();
	private static final ILogger LOGGER = MixinService.getService().getLogger("mixinsquared");

	@ApiStatus.Internal
	public static AdjustableAnnotationNode adjust(List<String> targetClassNames, String mixinClassName, MethodNode handlerNode, AdjustableAnnotationNode annotationNode, BiConsumer<String, AdjustableAnnotationNode> postAdjustmentConsumer) {
		for (MixinAnnotationAdjuster adjuster : adjusters) {
			if (annotationNode == null) {
				LOGGER.debug("Skipping annotation adjuster {} as the annotation has been removed", adjuster.getClass().getName());
				continue;
			}
			AnnotationEqualityVisitor equalityVisitor = new AnnotationEqualityVisitor(annotationNode.copy());
			annotationNode = adjuster.adjust(targetClassNames, mixinClassName, handlerNode, annotationNode);
			equalityVisitor.visit(annotationNode);
			if (!equalityVisitor.isEqual()) {
				postAdjustmentConsumer.accept(adjuster.getClass().getName(), annotationNode);
			}
		}
		return annotationNode;
	}

	public static void register(MixinAnnotationAdjuster adjuster) {
		adjusters.add(adjuster);
		LOGGER.debug("Registered annotation adjuster {}", adjuster.getClass().getName());
	}
}
