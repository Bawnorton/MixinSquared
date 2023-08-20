plugins {
    java
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    group = "com.bawnorton"
    version = "0.0.1"

    repositories {
        mavenCentral()
        maven("https://repo.spongepowered.org/maven")
        maven("https://jitpack.io")
    }

    dependencies {
        compileOnly("org.spongepowered:mixin:0.8.5")
        compileOnly("org.apache.commons:commons-lang3:3.3.2")
        compileOnly("org.ow2.asm:asm-debug-all:5.2")
        compileOnly("com.google.guava:guava:31.1-jre")
        compileOnly("com.github.llamalad7.mixinextras:mixinextras-common:0.2.0-beta.9")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        withSourcesJar()
    }

    tasks.withType<Jar> {
        archiveBaseName.set("mixinsquared-$moduleName")
    }

    extensions.configure<PublishingExtension> {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.bawnorton.mixinsquared"
                artifactId = "mixinsquared-$moduleName"

                from(components["java"])
            }
        }
    }
}

subprojects {
    apply(plugin = "java")

    if (project.name != "forge") {
        dependencies {
            compileOnly(rootProject)
        }

        tasks.named<Jar>("jar") {
            from(rootProject.sourceSets.main.get().output)
        }

        tasks.named<Jar>("sourcesJar") {
            from(rootProject.sourceSets.main.get().allSource)
        }
    }
}

tasks.withType<Jar> {
    from("LICENSE") {
        rename { "${it}_MixinSquared"}
    }
}

val Project.moduleName get () = if (parent == null) "common" else name