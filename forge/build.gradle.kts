repositories {
    maven("https://maven.minecraftforge.net/") {
        metadataSources {
            ignoreGradleMetadataRedirection()
            mavenPom()
        }
    }
}

dependencies {
    compileOnly(rootProject)
    compileOnly("net.minecraftforge:forge:1.16.4-35.1.37:universal")
    compileOnly("net.minecraftforge:forgespi:3.2.0")
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