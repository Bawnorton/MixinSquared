repositories {
    maven("https://maven.neoforged.net/")
}

dependencies {
    compileOnly(rootProject)
    compileOnly("net.neoforged.fancymodloader:language-java:1.0.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Jar> {
    from(rootProject.tasks.named<Jar>("jar").get().archiveFile) {
        rename { "META-INF/jars/MixinSquared-${project.version}.jar" }
    }
    from(rootProject.file("LICENSE")) {
        rename { "${it}_MixinSquared" }
    }
    manifest.attributes(
            "MixinConfigs" to "mixinsquared.init.mixins.json",
            "FMLModType" to "GAMELIBRARY",
    )
}

tasks.withType<ProcessResources> {
    inputs.property("version", version)

    filesMatching(listOf("META-INF/mods.toml", "META-INF/jarjar/metadata.json")) {
        expand("version" to version)
    }
}