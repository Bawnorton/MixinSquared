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
import com.bawnorton.mixinsquared.reflection.MixinInfoExtension;
import com.bawnorton.mixinsquared.reflection.StateExtension;
import com.bawnorton.mixinsquared.reflection.TargetClassContextExtension;
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

public final class ExtensionAnnotationAdjust implements IExtension {
    private static final ILogger LOGGER = MixinService.getService().getLogger("mixinsquared-annotation-adjuster");

    @Override
    public boolean checkActive(MixinEnvironment environment) {
        return true;
    }

    @Override
    public void preApply(ITargetClassContext context) {
        TargetClassContextExtension.tryAs(context).ifPresent(contextExtension -> {
            SortedSet<IMixinInfo> mixins = contextExtension.getMixins();
            mixins.forEach(mixin -> {
                ClassNode mixinClassNode = mixin.getClassNode(0);
                List<String> targetClassNames = mixin.getTargetClasses().stream().map(s -> s.replaceAll("/", ".")).collect(Collectors.toList());
                String mixinClassName = mixin.getClassName();

                List<MethodNode> methodNodes = mixinClassNode.methods;
                methodNodes.forEach(methodNode -> {
                    List<AnnotationNode> visibleAnnotations = methodNode.visibleAnnotations;
                    if (visibleAnnotations == null) return;

                    List<AnnotationNode> postAdjust = new ArrayList<>();
                    for (AnnotationNode annotationNode : visibleAnnotations) {
                        AdjustableAnnotationNode preAdjusted = AdjustableAnnotationNode.fromNode(annotationNode);
                        AdjustableAnnotationNode postAdjusted = MixinAnnotationAdjusterRegistrar.adjust(targetClassNames, mixinClassName, methodNode, preAdjusted);
                        if (postAdjusted != null) {
                            postAdjust.add(postAdjusted);
                        } else if (!methodNode.name.endsWith("m2_annotation_removed")) {
                            //noinspection StringConcatenationInLoop
                            methodNode.name += String.format("$%s$m2_annotation_removed", RandomStringUtils.randomAlphanumeric(6));
                        }
                        if(!equal(preAdjusted, postAdjusted)) {
                            LOGGER.debug("{} -> {}", annotationNode, postAdjusted);
                        }
                    }
                    visibleAnnotations.clear();
                    visibleAnnotations.addAll(postAdjust);
                });

                MixinInfoExtension.tryAs(mixin)
                        .flatMap(mixinExtension -> StateExtension.tryAs(mixinExtension.getState()))
                        .ifPresent(stateExtension -> stateExtension.setClassNode(mixinClassNode));
            });

        });
    }

    private boolean equal(AnnotationNode an1, AnnotationNode an2) {
        if(an1 == null) return an2 == null;
        if(an2 == null) return false;
        if(!an1.desc.equals(an2.desc)) return false;
        if(an1.values == null) return an2.values == null;
        if(an2.values == null) return false;
        if(an1.values.size() != an2.values.size()) return false;
        return an1.values.equals(an2.values);
    }


    @Override
    public void postApply(ITargetClassContext context) {

    }

    @Override
    public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {

    }
}
