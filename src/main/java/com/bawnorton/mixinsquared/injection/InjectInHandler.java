package com.bawnorton.mixinsquared.injection;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Modified version of {@link org.spongepowered.asm.mixin.injection.Inject} to target other mixin handlers
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectInHandler {

    String id() default "";

    String[] method() default {};

    Desc[] target() default {};

    Slice[] slice() default {};

    At[] at();

    boolean cancellable() default false;

    LocalCapture locals() default LocalCapture.NO_CAPTURE;

    boolean remap() default true;

    int require() default -1;

    int expect() default 1;

    int allow() default -1;

    String constraints() default "";

}
