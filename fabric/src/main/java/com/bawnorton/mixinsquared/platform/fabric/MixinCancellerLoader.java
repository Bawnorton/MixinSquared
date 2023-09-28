package com.bawnorton.mixinsquared.platform.fabric;

import com.bawnorton.mixinsquared.api.MixinCanceller;
import com.bawnorton.mixinsquared.canceller.MixinCancellerRegistrar;
import net.fabricmc.loader.api.FabricLoader;

public final class MixinCancellerLoader {
    public static void load() {
        FabricLoader.getInstance().getEntrypointContainers("mixinsquared", MixinCanceller.class).forEach(container -> {
            String id = container.getProvider().getMetadata().getId();
            try {
                MixinCanceller canceller = container.getEntrypoint();
                MixinCancellerRegistrar.register(canceller);
            } catch (Throwable e) {
                System.err.printf("Mod %s provides a broken MixinCanceller implementation:\n", id);
                e.printStackTrace(System.err);
            }
        });
    }
}
