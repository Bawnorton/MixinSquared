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

package com.bawnorton.mixinsquared.adjuster.tools;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.asm.ASM;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AdjustableAnnotationNode extends AnnotationNode {
    protected AdjustableAnnotationNode(AnnotationNode node) {
        super(ASM.API_VERSION, node.desc);
        node.accept(this);
    }

    public static AdjustableAnnotationNode fromNode(AnnotationNode annotationNode) {
        return KnownAnnotations.fromNode(annotationNode);
    }

    protected static <T extends AdjustableAnnotationNode> List<T> fromList(List<AnnotationNode> nodes, AdjustableAnnotationNodeFactory<T> factory) {
        List<T> list = new ArrayList<>(nodes.size());
        for(AnnotationNode node : nodes) {
            list.add(factory.create(node));
        }
        return list;
    }

    public boolean is(Class<? extends Annotation> annotation) {
        return desc.equals(Type.getDescriptor(annotation));
    }

    public <T extends AdjustableAnnotationNode> T as(Class<T> type) {
        if(type.isInstance(this)) {
            return type.cast(this);
        }
        throw new ClassCastException(String.format(
                "Cannot cast %s to %s",
                this.getClass().getSimpleName(),
                type.getSimpleName()
        ));
    }

    public <T> Optional<T> get(String key) {
        return Optional.ofNullable(Annotations.getValue(this, key));
    }

    public <T extends Enum<T>> Optional<T> getEnum(String key, Class<T> enumType) {
        return this.<String[]>get(key).map(value -> {
            if(value.length < 2) return null;
            if(enumType.getName().equals(Type.getType(value[0]).getClassName())) {
                return Enum.valueOf(enumType, value[1]);
            }
            return null;
        });
    }

    public <T> void set(String key, T value) {
        Annotations.setValue(this, key, value);
    }

    protected enum KnownAnnotations {
        AT(AdjustableAtNode::new, At.class),
        CONSTANT(AdjustableConstantNode::new, Constant.class),
        DESC(AdjustableDescNode::new, Desc.class),
        NEXT(AdjustableNextNode::new, "Lorg/spongepowered/asm.mixin/injection/Next"),
        SLICE(AdjustableSliceNode::new, Slice.class),
        INJECT(AdjustableInjectNode::new, Inject.class),
        MODIFY_ARG(AdjustableModifyArgNode::new, ModifyArg.class),
        MODIFY_ARGS(AdjustableModifyArgsNode::new, ModifyArgs.class),
        MODIFY_CONSTANT(AdjustableModifyConstantNode::new, ModifyConstant.class),
        MODIFY_EXPRESSION_VALUE(AdjustableModifyExpressionValueNode::new, "Lcom/llamalad7/mixinextras/injector/ModifyExpressionValue;"),
        MODIFY_RECIEVER(AdjustableModifyReceiverNode::new, "Lcom/llamalad7/mixinextras/injector/ModifyReceiver;"),
        MODIFY_RETURN_VALUE(AdjustableModifyReturnValueNode::new, "Lcom/llamalad7/mixinextras/injector/ModifyReturnValue;"),
        MODIFY_VARIABLE(AdjustableModifyVariableNode::new, ModifyVariable.class),
        OVERWRITE(AdjustableOverwriteNode::new, Overwrite.class),
        REDIRECT(AdjustableRedirectNode::new, Redirect.class),
        WRAP_OPERATION(AdjustableWrapOperationNode::new, "Lcom/llamalad7/mixinextras/injector/wrapoperation/WrapOperation;"),
        WRAP_WITH_CONDITION(AdjustableWrapWithConditionNode::new, "Lcom/llamalad7/mixinextras/injector/v2/WrapWithCondition;");

        private final AdjustableAnnotationNodeFactory<?> factory;
        private final String annotationClassDesc;

        KnownAnnotations(AdjustableAnnotationNodeFactory<?> factory, Class<? extends Annotation> annotationClass) {
            this(factory, Type.getDescriptor(annotationClass));
        }

        KnownAnnotations(AdjustableAnnotationNodeFactory<?> factory, String annotationClassDesc) {
            this.factory = factory;
            this.annotationClassDesc = annotationClassDesc;
        }

        public AdjustableAnnotationNode create(AnnotationNode node) {
            return factory.create(node);
        }

        public String desc() {
            return annotationClassDesc;
        }

        public static AdjustableAnnotationNode fromNode(AnnotationNode node) {
            for(KnownAnnotations annotation : values()) {
                if(node.desc.equals(annotation.annotationClassDesc)) {
                    return annotation.create(node);
                }
            }
            return new AdjustableAnnotationNode(node) {};
        }
    }

    protected interface AdjustableAnnotationNodeFactory<T extends AdjustableAnnotationNode> {
        T create(AnnotationNode node);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('@').append(desc);
        sb.append('(');
        if(values == null) {
            sb.append(')');
            return sb.toString();
        }
        Map<String, Object> valueMap = new HashMap<>();
        for(int i = 0; i < values.size(); i += 2) {
            String key = (String) values.get(i);
            Object value = values.get(i + 1);
            if (value instanceof String) {
                valueMap.put(key, '"' + value.toString() + '"');
            } else if (value instanceof Type) {
                valueMap.put(key, ((Type) value).getClassName() + ".class");
            } else if (value instanceof AnnotationNode) {
                valueMap.put(key, AdjustableAnnotationNode.fromNode((AnnotationNode) value).toString());
            } else if (value instanceof List) {
                List<?> list = (List<?>) value;
                if (list.isEmpty()) {
                    valueMap.put(key, "{}");
                } else {
                    StringBuilder listBuilder = new StringBuilder();
                    listBuilder.append('{');
                    for (int j = 0; j < list.size(); j++) {
                        Object element = list.get(j);
                        if (element instanceof String) {
                            listBuilder.append('"').append(element).append('"');
                        } else if (element instanceof Type) {
                            listBuilder.append(((Type) element).getClassName()).append(".class");
                        } else if (element instanceof AnnotationNode) {
                            listBuilder.append(AdjustableAnnotationNode.fromNode((AnnotationNode) element).toString());
                        } else {
                            listBuilder.append(element);
                        }
                        if (j < list.size() - 1) {
                            listBuilder.append(", ");
                        }
                    }
                    listBuilder.append('}');
                    valueMap.put(key, listBuilder.toString());
                }
            } else {
                valueMap.put(key, value);
            }
        }
        for(Map.Entry<String, Object> entry : valueMap.entrySet()) {
            sb.append(entry.getKey()).append('=').append(entry.getValue());
            sb.append(", ");
        }
        if(!valueMap.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(')');
        return sb.toString();
    }
}
