![MixinSquared Logo](https://github.com/Bawnorton/MixinSquared/assets/18416784/95078218-907e-4280-b5dc-5dda6b771b5f)

A [Mixin](https://github.com/SpongePowered/Mixin/) library for mixin'ing into other mod's mixins.

See the [wiki](https://github.com/Bawnorton/MixinSquared/wiki) for usage

Works with [MixinExtras](https://github.com/LlamaLad7/MixinExtras)

### Plugin

An MCDev Addon IntellJ Plugin can be installed from [here](https://plugins.jetbrains.com/plugin/26828-mixinsquared) which provides syntax highlighting and suggestions when using MixinSquared

## Gradle

```gradle
repositories {
    maven { url = "https://maven.bawnorton.com/releases" }

    // Mirror
    maven { url = "https://maven.enjarai.dev/mirrors" }
}
```

For each platform:<br>
<details><summary>Fabric / Quilt</summary>

```gradle
dependencies {
    include(implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.5-beta.2")))
}
```

</details>
<details><summary>Forge 1.18.2+ with ForgeGradle</summary>

### This will not work for Forge 1.18.1 and below, see `Any Other Platform`

```gradle
dependencies {
    // MixinSquared's annotationProcessor MUST be registered BEFORE Mixin's one.
    compileOnly(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.5-beta.2"))
    implementation(jarJar("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.3.5-beta.2")) {
        jarJar.ranged(it, "[0.3.5-beta.2,)")
    }
}
```

</details>
<details><summary>NeoForge with NeoGradle</summary>

```gradle
dependencies {
    // MixinSquared's annotationProcessor MUST be registered BEFORE Mixin's one.
    compileOnly(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.5-beta.2"))
    implementation(jarJar("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.3.5-beta.2")) {
        jarJar.ranged(it, "[0.3.5-beta.2,)")
    }
}
```

</details>
<details><summary>Forge 1.18.2+ with Architectury Loom</summary>

### This will not work for Forge 1.18.1 and below, see `Any Other Platform`

```gradle
dependencies {
    compileOnly(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.5-beta.2"))
    implementation(include("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.3.5-beta.2"))
}
```

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
    maven { url = "https://maven.bawnorton.com" }
}

dependencies {
    shadow(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.5-beta.2"))
}

shadowJar {
    configurations = [project.configurations.shadow]
    relocate("com.bawnorton.mixinsquared", "your.package.goes.here.mixinsquared")
    mergeServiceFiles()
}
```

To initialize MixinSquared yourself, implement a [IMixinConfigPlugin](https://jenkins.liteloader.com/view/Other/job/Mixin/javadoc/org/spongepowered/asm/mixin/extensibility/IMixinConfigPlugin.html) and call

```java
MixinSquaredBootstrap.init();
MixinSquaredApiImplLoader.load();
```
In the `onLoad` method and
```java
MixinSquaredBootstrap.reOrderExtenstions();
```
In the `getMixins` method<br>

It is necessary for MixinSquared's extensions to be applied before MixinExtra's extensions to avoid
passing half-processed sugar annotations to the `MixinAnnotationAdjuster`

</details>

### Credits

Massive thanks to [LlamaLad7](https://github.com/LlamaLad7) for providing insight and feedback on this library.
