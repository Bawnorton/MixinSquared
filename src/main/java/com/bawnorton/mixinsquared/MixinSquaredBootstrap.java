package com.bawnorton.mixinsquared;

import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;

public class MixinSquaredBootstrap {
    public static final String NAME = "mixinsquared";
    public static final String VERSION = "0.0.1";

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;

        initialized = true;

        TargetSelector.register(DynamicSelectorHandler.class, "MixinSquared");
    }
}