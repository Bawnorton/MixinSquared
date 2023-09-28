package com.bawnorton.mixinsquared.platform.forge;

import com.bawnorton.mixinsquared.api.MixinCanceller;
import com.bawnorton.mixinsquared.canceller.MixinCancellerRegistrar;

import java.util.ServiceLoader;

public final class MixinCancellerLoader {
    private static final ServiceLoader<MixinCanceller> ENTRYPOINTS = ServiceLoader.load(MixinCanceller.class);

    public static void load() {
        ENTRYPOINTS.forEach(MixinCancellerRegistrar::register);
    }
}
