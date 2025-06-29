plugins {
    java
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    group = "com.bawnorton"
    version = "0.3.3"

    repositories {
        mavenCentral()
        maven("https://repo.spongepowered.org/maven")
    }

    dependencies {
        compileOnly("org.spongepowered:mixin:0.8.6")
        compileOnly("org.apache.commons:commons-lang3:3.3.2")
        compileOnly("org.ow2.asm:asm-debug-all:5.2")
        compileOnly("io.github.llamalad7:mixinextras-common:0.4.1")
        compileOnly("org.jetbrains:annotations:26.0.2")
        compileOnly("com.google.guava:guava:31.1-jre")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        withSourcesJar()
    }

    tasks.withType<Jar> {
        archiveBaseName.set("mixinsquared-$moduleName")
    }

    tasks.register<Copy>("buildAndCollect") {
        group = "build"
        val mainJar = tasks.jar.get().archiveFile
        val sourceJar = tasks.named<Jar>("sourcesJar").get().archiveFile
        from(mainJar, sourceJar)
        into(rootProject.layout.buildDirectory.dir("libs/${version}"))
        dependsOn("build", "sourcesJar")
    }

    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                name = "bawnorton"
                url = uri("https://maven.bawnorton.com/releases")
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.github.bawnorton.mixinsquared"
                artifactId = "mixinsquared-$moduleName"

                from(components["java"])
            }
        }
    }
}

subprojects {
    apply(plugin = "java")

    if (project.name == "fabric") {
        dependencies {
            compileOnly(rootProject)
        }

        tasks.named<Jar>("jar") {
            from(rootProject.sourceSets.main.get().output)
        }
    }

    tasks.named<Jar>("sourcesJar") {
        from(rootProject.sourceSets.main.get().allSource)
    }
}

tasks.withType<Jar> {
    from("LICENSE") {
        rename { "${it}_MixinSquared" }
    }
}

val Project.moduleName get() = if (parent == null) "common" else name