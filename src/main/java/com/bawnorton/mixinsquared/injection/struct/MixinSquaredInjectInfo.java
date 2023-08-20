package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.selectors.HandlerInfo;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.selectors.ISelectorContext;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.InvalidSelectorException;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.TargetNotSupportedException;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;

import java.util.Set;

public interface MixinSquaredInjectInfo extends ISelectorContext {
    default void parseSelectors() {
        HandlerInfo.parse(Annotations.getValue(getAnnotationNode(), "method", false), this, getSelectors());
        HandlerInfo.parse(Annotations.getValue(getAnnotationNode(), "target", false), this, getSelectors());

        if (getSelectors().isEmpty()) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing 'method' or 'target' to specify targets",
                    getAnnotationType(), getMethodName()));
        }

        for (ITargetSelector selector : getSelectors()) {
            try {
                getSelectors().add(selector.validate().attach(this));
            } catch (InvalidMemberDescriptorException ex) {
                throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: %s. %s",
                        getAnnotationType(), getMethodName(), ex.getMessage(), getMixin().getReferenceMapper().getStatus()));
            } catch (TargetNotSupportedException ex) {
                throw new InvalidInjectionException(this,
                        String.format("%s annotation on %s specifies a target class '%s', which is not supported",
                                getAnnotationType(), getMethodName(), ex.getMessage()));
            } catch (InvalidSelectorException ex) {
                throw new InvalidInjectionException(this,
                        String.format("%s annotation on %s is decorated with an invalid selector: %s", getAnnotationType(), getMethodName(),
                                ex.getMessage()));
            }
        }
    }

    Set<ITargetSelector> getSelectors();

    String getAnnotationType();

    String getMethodName();

    AnnotationNode getAnnotationNode();
}
