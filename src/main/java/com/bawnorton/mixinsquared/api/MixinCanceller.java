package com.bawnorton.mixinsquared.api;

import java.util.List;

public interface MixinCanceller {
    boolean shouldCancel(List<String> targetClassNames, String mixinClassName);
}
