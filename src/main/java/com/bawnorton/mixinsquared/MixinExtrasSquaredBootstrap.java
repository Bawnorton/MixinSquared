package com.bawnorton.mixinsquared;

import com.bawnorton.mixinsquared.mixinextras.injection.struct.ModifyExpressionValueInHandlerInfo;
import com.bawnorton.mixinsquared.mixinextras.injection.struct.ModifyReceiverInHandlerInfo;
import com.bawnorton.mixinsquared.mixinextras.injection.struct.ModifyReturnValueInHandlerInfo;
import com.bawnorton.mixinsquared.mixinextras.injection.struct.WrapWithConditionInHandlerInfo;
import com.bawnorton.mixinsquared.mixinextras.injection.struct.WrapOperationInHandlerInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

public class MixinExtrasSquaredBootstrap {
    static void init() {
        InjectionInfo.register(ModifyExpressionValueInHandlerInfo.class);
        InjectionInfo.register(ModifyReceiverInHandlerInfo.class);
        InjectionInfo.register(ModifyReturnValueInHandlerInfo.class);
        InjectionInfo.register(WrapOperationInHandlerInfo.class);
        InjectionInfo.register(WrapWithConditionInHandlerInfo.class);
    }
}
