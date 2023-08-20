package com.bawnorton.mixinsquared;

import com.bawnorton.mixinsquared.injection.struct.*;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

public class MixinSquaredBootstrap {
	public static final String NAME = "mixinsquared";
	public static final String VERSION = "0.0.1";

	private static boolean initialized = false;

	public static void init() {
		if (initialized) return;

		initialized = true;

		InjectionInfo.register(InjectInHandlerInjectionInfo.class);
		InjectionInfo.register(ModifyArgInHandlerInjectionInfo.class);
		InjectionInfo.register(ModifyArgsInHandlerInjectionInfo.class);
		InjectionInfo.register(ModifyConstantInHandlerInjectionInfo.class);
		InjectionInfo.register(ModifyVariableInHandlerInjectionInfo.class);
		InjectionInfo.register(RedirectInHandlerInjectionInfo.class);

		try {
			Class.forName("com.llamalad7.mixinextras.MixinExtrasBootstrap");
		} catch (ClassNotFoundException e) {
			return;
		}

		MixinExtrasSquaredBootstrap.init();
	}
}