# MixinSquared
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
    include(implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.0.2")))
}
```

</details>
<details><summary>Forge 1.18.2+ with ForgeGradle</summary>

```gradle
dependencies {
    // MixinSquared's annotationProcessor MUST be registered BEFORE Mixin's one.
    implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.0.2"))
    implementation(jarJar("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.0.2")) {
        jarJar.ranged(it, "[0.0.2,)")
    }
}
```

</details>
<details><summary>Forge 1.18.2+ with Architectury Loom</summary>

```gradle
dependencies {
    implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.0.2"))
    implementation(include("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.0.2"))
}
```
</details>

## Initialization

If you are on Fabric, Quilt or Forge 1.18.2+ MixinSquared will automatically initiate, otherwise call:
```java
MixinSquaredBootstrap.init();
```
In the `onLoad` method inside a [IMixinConfigPlugin](https://jenkins.liteloader.com/view/Other/job/Mixin/javadoc/org/spongepowered/asm/mixin/extensibility/IMixinConfigPlugin.html)

### Credits
Massive thanks to [LlamaLad7](https://github.com/LlamaLad7) for providing insight and feedback on this library.
