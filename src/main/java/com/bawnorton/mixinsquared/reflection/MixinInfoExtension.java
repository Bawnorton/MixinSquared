package com.bawnorton.mixinsquared.reflection;

import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.function.Consumer;

@ApiStatus.Internal
public final class MixinInfoExtension {
	private final IMixinInfo reference;

	private final MethodReference<?> stateMethod;
	private final MethodReference<String> remapClassNameMethod;

	private MixinInfoExtension(IMixinInfo reference) {
		this.reference = reference;
		this.stateMethod = new MethodReference<>(reference.getClass(), "getState");
		this.remapClassNameMethod = new MethodReference<>(reference.getClass(), "remapClassName", String.class);
	}

	public static void tryAs(IMixinInfo mixinInfo, Consumer<MixinInfoExtension> consumer) {
		if (mixinInfo.getClass().getName().equals("org.spongepowered.asm.mixin.transformer.MixinInfo")) {
			consumer.accept(new MixinInfoExtension(mixinInfo));
		}
	}

	public Object getState() {
		return stateMethod.invoke(reference);
	}

	public String remapClassName(String className) {
		return remapClassNameMethod.invoke(reference, className);
	}
}
