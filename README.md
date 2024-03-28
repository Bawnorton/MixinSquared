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
    include(implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.1.2-beta.5")))
}
```

</details>
<details><summary>Forge 1.18.2+ with ForgeGradle</summary>

### This will not work for Forge 1.18.1 and below, see `Any Other Platform`

```gradle
dependencies {
    // MixinSquared's annotationProcessor MUST be registered BEFORE Mixin's one.
    implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.1.2-beta.5"))
    implementation(jarJar("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.1.2-beta.5")) {
        jarJar.ranged(it, "[0.1.2-beta.5,)")
    }
}
```

</details>
<details><summary>NeoForge with NeoGradle</summary>

```gradle
dependencies {
    // MixinSquared's annotationProcessor MUST be registered BEFORE Mixin's one.
    implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.1.2-beta.5"))
    implementation(jarJar("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.1.2-beta.5")) {
        jarJar.ranged(it, "[0.1.2-beta.5,)")
    }
}
```

</details>
<details><summary>Forge 1.18.2+ with Architectury Loom</summary>

### This will not work for Forge 1.18.1 and below, see `Any Other Platform`

```gradle
dependencies {
    implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.1.2-beta.5"))
    implementation(include("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.1.2-beta.5"))
}
```

</details>

</details>
<details><summary>Any Other Platform</summary>

This is only a rough guide. You will need to look into the specifics of setting up ShadowJar for your platform.

```gradle
plugins {
    id "com.github.johnrengelman.shadow" version "8.1.0"
}

configurations {
    implementation.extendsFrom shadow
}

repositories {
    maven { url = "https://jitpack.io" }
}

dependencies {
    shadow(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.1.2-beta.5"))
}

shadowJar {
    configurations = [project.configurations.shadow]
    relocate("com.github.bawnorton.mixinsquared", "your.package.goes.here.mixinsquared")
    mergeServiceFiles()
}
```

To initialize MixinSquared, simply call

```java
MixinSquaredBootstrap.init();
```
In the `onLoad` method inside a [IMixinConfigPlugin](https://jenkins.liteloader.com/view/Other/job/Mixin/javadoc/org/spongepowered/asm/mixin/extensibility/IMixinConfigPlugin.html)

If you intend to use a MixinCanceller, you will need to also call:
```
MixinCancellerRegistrar.register(new YourMixinCancellerImpl());
```

</details>

### Credits

Massive thanks to [LlamaLad7](https://github.com/LlamaLad7) for providing insight and feedback on this library.
