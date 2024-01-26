pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        gradlePluginPortal()
    }
}

rootProject.name = "MixinSquared"
include("fabric")
include("forge")
include("neoforge")