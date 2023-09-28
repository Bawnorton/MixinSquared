package com.bawnorton.mixinsquared.canceller;

import com.bawnorton.mixinsquared.api.MixinCanceller;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.service.MixinService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class MixinCancellerRegistrar {
    private static final Set<MixinCanceller> cancellers = new HashSet<>();
    private static final ILogger LOGGER = MixinService.getService().getLogger("mixinsquared");

    public static boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        return cancellers.stream().anyMatch(canceller -> canceller.shouldCancel(targetClassNames, mixinClassName));
    }

    public static void register(MixinCanceller canceller) {
        cancellers.add(canceller);
        LOGGER.debug("Registered canceller {}", canceller.getClass().getName());
    }
}
