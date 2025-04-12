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
import com.bawnorton.mixinsquared.adjuster.tools.type.RemappableAnnotationNode;
import com.bawnorton.mixinsquared.ext.MixinSquaredExtension;
import com.bawnorton.mixinsquared.reflection.MixinInfoExtension;
import com.bawnorton.mixinsquared.reflection.StateExtension;
import com.bawnorton.mixinsquared.reflection.TargetClassContextExtension;
import com.bawnorton.mixinsquared.util.AnnotationEqualityVisitor;
import org.apache.commons.lang3.RandomStringUtils;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.service.MixinService;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public final class ExtensionAnnotationAdjust implements IExtension, MixinSquaredExtension {
    private static final ILogger LOGGER = MixinService.getService().getLogger("mixinsquared-annotation-adjuster");

    @Override
    public boolean checkActive(MixinEnvironment environment) {
        return true;
    }

    @Override
    public void preApply(ITargetClassContext context) {
        TargetClassContextExtension.tryAs(context, contextExtension -> {
            SortedSet<IMixinInfo> mixinInfos = contextExtension.getMixins();
            mixinInfos.forEach(mixinInfo -> MixinInfoExtension.tryAs(mixinInfo, mixinInfoExtension -> {
                ClassNode mixinClassNode = mixinInfo.getClassNode(0);
                List<String> targetClassNames = mixinInfo.getTargetClasses().stream().map(s -> s.replaceAll("/", ".")).collect(Collectors.toList());
                String mixinClassName = mixinInfo.getClassName();

                List<MethodNode> methodNodes = mixinClassNode.methods;
                methodNodes.forEach(methodNode -> {
                    List<AnnotationNode> visibleAnnotations = methodNode.visibleAnnotations;
                    if (visibleAnnotations == null) return;

                    List<AdjustableAnnotationNode> postAdjust = new ArrayList<>();
                    for (AnnotationNode annotationNode : visibleAnnotations) {
                        AdjustableAnnotationNode preAdjusted = AdjustableAnnotationNode.fromNode(annotationNode);
                        if(preAdjusted instanceof RemappableAnnotationNode) {
                            RemappableAnnotationNode remappable = (RemappableAnnotationNode) preAdjusted;
                            remappable.setRemapper(node -> node.applyRefmap(mixinInfoExtension::remapClassName));
                        }
                        AdjustableAnnotationNode postAdjusted = MixinAnnotationAdjusterRegistrar.adjust(targetClassNames, mixinClassName, methodNode, preAdjusted, (adjuster, node) -> {
                            LOGGER.warn("Modified mixin \"{}\". Check debug logs for more information.", mixinClassName);
                            LOGGER.debug("Adjuster \"{}\" modified annotation on method \"{}\" in mixinInfo \"{}\"", adjuster, methodNode.name + methodNode.desc, mixinClassName);
                            LOGGER.debug("Pre-adjustment: {}", preAdjusted);
                            LOGGER.debug("Post-adjustment: {}", node == null ? "null" : node);
                        });
                        if (postAdjusted != null) {
                            postAdjust.add(postAdjusted);
                        }
                    }
                    visibleAnnotations.clear();
                    visibleAnnotations.addAll(postAdjust);
                });

                StateExtension.tryAs(mixinInfoExtension.getState(), stateExtension -> stateExtension.setClassNode(mixinClassNode));
            }));

        });
    }

    @Override
    public void postApply(ITargetClassContext context) {

    }

    @Override
    public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {

    }
}
