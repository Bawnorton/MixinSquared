package com.bawnorton.mixinsquared.canceller;

import com.bawnorton.mixinsquared.api.MixinCanceller;
import org.spongepowered.asm.logging.ILogger;
import org.spongepowered.asm.service.MixinService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class MixinCancellerRegistrar {
    private static final Set<MixinCanceller> cancellers = new HashSet<>();
    private static final ILogger LOGGER = MixinService.getService().getLogger("mixinsquared");

    public static boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        return cancellers.stream().anyMatch(canceller -> {
            if(canceller.shouldCancel(targetClassNames, mixinClassName)) {
                return true;
            } else if(canceller.shouldCancel(targetClassNames.stream().map(s -> s.replaceAll("\\.", "/")).collect(Collectors.toList()), "")) {
                LOGGER.warn("Canceller {} is matching with a deprecated target class format, use \".\" instead of \"/\"", canceller.getClass().getName());
                return true;
            }
            return false;
        });
    }

    public static void register(MixinCanceller canceller) {
        cancellers.add(canceller);
        LOGGER.debug("Registered canceller {}", canceller.getClass().getName());
    }
}
