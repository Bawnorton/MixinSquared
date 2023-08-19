package com.bawnorton.mixinsquared.injection;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Slice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Modified version of {@link org.spongepowered.asm.mixin.injection.ModifyArg} to target other mixin handlers
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyArgInHandler {
    String[] method() default {};

    Desc[] target() default {};

    Slice slice() default @Slice;

    At at();

    int index() default -1;

    boolean remap() default true;

    int require() default -1;

    int expect() default 1;
    
    int allow() default -1;

    String constraints() default "";
}
