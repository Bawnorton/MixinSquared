![MixinSquared Logo 2](https://github.com/Bawnorton/MixinSquared/assets/18416784/95078218-907e-4280-b5dc-5dda6b771b5f)

A [Mixin](https://github.com/SpongePowered/Mixin/) library for mixin'ing into other mod's mixins.

See the [wiki](https://github.com/Bawnorton/MixinSquared/wiki) for usage

Works with [MixinExtras](https://github.com/LlamaLad7/MixinExtras)

## Gradle

```gradle
repositories {
    maven { url = "https://jitpack.io" }
}
```

For each platform:<br>
<details><summary>Fabric / Quilt</summary>

```gradle
dependencies {
    include(implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.1.2-beta.3")))
}
```

</details>
<details><summary>Forge 1.18.2+ with ForgeGradle</summary>

```gradle
dependencies {
    // MixinSquared's annotationProcessor MUST be registered BEFORE Mixin's one.
    implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.1.2-beta.3"))
    implementation(jarJar("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.1.2-beta.3")) {
        jarJar.ranged(it, "[0.1.2-beta.3,)")
    }
}
```

</details>
<details><summary>NeoForge with NeoGradle</summary>

```gradle
dependencies {
    // MixinSquared's annotationProcessor MUST be registered BEFORE Mixin's one.
    implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.1.2-beta.3"))
    implementation(jarJar("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.1.2-beta.3")) {
        jarJar.ranged(it, "[0.1.2-beta.3,)")
    }
}
```

</details>
<details><summary>Forge 1.18.2+ with Architectury Loom</summary>

```gradle
dependencies {
    implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.1.2-beta.3"))
    implementation(include("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.1.2-beta.3"))
}
```

</details>

## Initialization

If you are on Fabric, Quilt or Forge 1.18.2+ MixinSquared will automatically initiate, otherwise you will need to call:

```java
MixinSquaredBootstrap.init();
```

In the `onLoad` method inside
a [IMixinConfigPlugin](https://jenkins.liteloader.com/view/Other/job/Mixin/javadoc/org/spongepowered/asm/mixin/extensibility/IMixinConfigPlugin.html)

### Credits

Massive thanks to [LlamaLad7](https://github.com/LlamaLad7) for providing insight and feedback on this library.
