# Target Handler

The `@TargetHandler` selector allows any injector to target a handler that was added to the mixin'd class.

The handler takes in two required and two optional arguments:

- `mixin`: The fully qualified class name for the mixin the handler originates from.
    - Required
- `name`: The name of the handler method.
    - Required
    - This must be the exact name of the handler in the target mixin if the handler is prefixed with the modid, then the
      `name` argument must also.
- `prefix`: The prefix added to the front of handler methods by Mixin.
    - Optional, will match the first prefix if not specified.
    - See the [table](#prefixes) below for the prefixes.
- `print`: Print all possible candidates for the selector
    - Optional
    - Due to the limitations of selectors, this will print until the first match is found.

When targetting a mixin'd handler with an injector, set the method parameter to `@MixinSquared:Handler` to delegate the
targeting to the dynamic selector.

## Example:

Let's say the following mixin exists:

```java
@Mixin(TargetClass.class)
public abstract class TargetClassMixin {
    @Redirect(
        method = "targetMethod", 
        at = @At(
            value = "INVOKE", 
            target = "Lpackage/to/TargetClass;damage(Lpackage/to/class/DamageSource;F)Z"
        )
    )
    private boolean handleDamage(TargetClass instance, DamageSource source, float amount) {
        if(!AnotherMod.handleDamage(source, amount)) {
            LOGGER.warn("Could not handle damage");
        }
    }
}
```

You may want to stop this mixin from always giving a warning when the mod doesn't handle the damage, and instead reduce
the level to debug.

This can be done like so:

```java
@Mixin(value = TargetClass.class, priority = 1500) // priority is higher than the target mixin
public abstract class TargetClassMixinSquared {
    @TargetHandler(
        mixin = "com.bawnorton.mixin.TargetClassMixin", 
        name = "handleDamage"
    )
    @Redirect(
        method = "@MixinSquared:Handler", 
        at = @At(
            value = "INVOKE", 
            target = "org/slf4j/Logger.warn(Ljava/lang/String;)V"
        )
    )
    private void reduceLogLevel(Logger instance, String message) {
        instance.debug(message);
    }
}
```

The target handler can target other mod's mixin squared mixins as long as the priority is higher.

### More Examples:

<details>
    <summary>Targeting a handler with a specific prefix</summary>

Target mixin:

```java
@Mixin(TargetClass.class)
public abstract class TargetClassMixin {
    @Inject(method = "targetMethod", at = @At("HEAD"))
    private void targetMethod(CallbackInfo ci) {
        AnotherMod.doSomething();
    }
    
    @ModifyArg(
            method = "targetMethod", 
            at = @At(
                    value = "INVOKE", 
                    target = "Lpackage/to/TargetClass;someMethod(Lpackage/to/class/SomeClass;IIF)V"
            )
    )
    private float targetMethod(float arg) {
        return arg * 2;
    }
}
```

Targeting `@Inject` handler:

```java
@Mixin(value = TargetClass.class, priority = 1500)
public abstract class TargetClassMixinSquared {
    @TargetHandler(
        mixin = "com.bawnorton.mixin.TargetClassMixin", 
        name = "targetMethod", 
        prefix = "handler"
    )
    @Inject(method = "@MixinSquared:Handler", at = @At("HEAD"))
    private void doSomething(CallbackInfo originalCi, CallbackInfo ci) {
        LOGGER.info("Injecting into handler$######$modid$targetMethod");
    }
}
```

Targeting `@ModifyArg` handler:

```java
@Mixin(value = TargetClass.class, priority = 1500)
public abstract class TargetClassMixinSquared {
    @TargetHandler(
        mixin = "com.bawnorton.mixin.TargetClassMixin", 
        name = "targetMethod", 
        prefix = "modify"
    )
    @Inject(method = "@MixinSquared:Handler", at = @At("HEAD"))
    private void doSomething(float arg, CallbackInfo ci) {
        LOGGER.info("Injecting into modify$######$modid$targetMethod");
    }
}
```

</details>
<details>
    <summary>Targeting handler that explicitly contains the modid</summary>

Target mixin:

```java
@Mixin(TargetClass.class)
public abstract class TargetClassMixin {
    @Inject(method = "targetMethod", at = @At("HEAD"))
    private void modid$targetMethod(CallbackInfo ci) {
        AnotherMod.doSomething();
    }
}
```

Targeting the handler:

```java
@Mixin(value = TargetClass.class, priority = 1500)
public abstract class TargetClassMixinSquared {
    @TargetHandler(
        mixin = "com.bawnorton.mixin.TargetClassMixin", 
        name = "modid$targetMethod"
    )
    @Inject(method = "@MixinSquared:Handler", at = @At("HEAD"))
    private void doSomething(CallbackInfo originalCi, CallbackInfo ci) {
        LOGGER.info("Injecting into modid$targetMethod");
    }
}
```

</details>
<details>
    <summary>Targeting and using MixinExtras injectors</summary>

Target mixin:

```java
@Mixin(TargetClass.class)
public abstract class TargetClassMixin {
    @WrapOperation(
        method = "targetMethod", 
        at = @At(
            value = "INVOKE", 
            target = "Lpackage/to/TargetClass;someMethod(Lpackage/to/class/SomeClass;IIF)Z"
        )
    )
    private boolean targetMethod(TargetClass instance, SomeClass someClass, int i, int j, float f, Operation<Boolean> original) {
        boolean originalResult = original.call(instance, someClass, i, j, f);
        return originalResult || AnotherMod.someMethod(someClass);
    }
}

```

Changing the returned value of the `AnotherMod.someMethod` call:

```java
@Mixin(value = TargetClass.class, priority = 1500)
public abstract class TargetClassMixinSquared {
    @TargetHandler(
        mixin = "com.bawnorton.mixin.TargetClassMixin", 
        name = "targetMethod"
    )
    @ModifyExpressionValue(
            method = "@MixinSquared:Handler", 
            at = @At(
                    value = "INVOKE", 
                    target = "Lpackage/to/AnotherMod;someMethod(Lpackage/to/class/SomeClass;)Z"
            )
    )
    private boolean checkOwnMethod(boolean original) {
        return original && YourMod.someMethod();
    }
}
```

</details>

## Prefixes:

<table>
    <tr>
        <th>Annotation</th>
        <th>Prefix</th>
    </tr>
    <tr>
        <td><b>@Inject</b></td>
        <td>handler</td>
    </tr>
    <tr>
        <td><b>@ModifyArg</b></td>
        <td>modify</td>
    </tr>
    <tr>
        <td><b>@ModifyArgs</b></td>
        <td>args</td>
    </tr>
    <tr>
        <td><b>@ModifyConstant</b></td>
        <td>constant</td>
    </tr>
    <tr>
        <td><b>@ModifyVariable</b></td>
        <td>localvar</td>
    </tr>
    <tr>
        <td><b>@Redirect</b></td>
        <td>redirect</td>
    </tr>
    <tr>
        <td colspan="2"><b>Mixin Extras</b></td>
    </tr>
    <tr>
        <td><b>@ModifyExpressionValue</b></td>
        <td>modifyExpressionValue</td>
    </tr>
    <tr>
        <td><b>@ModifyReciever</b></td>
        <td>modifyReciever</td>
    </tr>
    <tr>
        <td><b>@ModifyReturnValue</b></td>
        <td>modifyReturnValue</td>
    </tr>
    <tr>
        <td><b>@WrapWithCondition</b></td>
        <td>wrapWithCondition</td>
    </tr>
    <tr>
        <td><b>@WrapOperation</b></td>
        <td>wrapOperation</td>
    </tr>
</table>