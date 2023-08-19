package com.bawnorton.mixinsquared.injection.struct;

import com.bawnorton.mixinsquared.injection.selectors.HandlerInfo;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

public abstract class MixinSquaredInjectInfo extends InjectionInfo {
    protected MixinSquaredInjectInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    protected MixinSquaredInjectInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
        super(mixin, method, annotation, atKey);
    }

    @Override
    protected void parseSelectors() {
        try {
            super.parseSelectors();
        } catch (InvalidInjectionException ignored) {
            // we allow for more lenient parsing of selectors, notably to catch cases
            // where modids previously contain invalid method characters such as fabric api's modules
        }
        HandlerInfo.parse(Annotations.getValue(this.annotation, "method", false), this, this.selectors);
    }
}
