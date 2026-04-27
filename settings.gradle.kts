pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.isxander.dev/releases/")
        gradlePluginPortal()
    }
}

rootProject.name = "MixinSquared"
include("fabric")
include("forge")
include("neoforge")