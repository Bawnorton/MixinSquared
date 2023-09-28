dependencies {
    compileOnly(rootProject)
    compileOnly("net.fabricmc:fabric-loader:0.14.11")
}

tasks.withType<ProcessResources> {
    inputs.property("version", version)

    filesMatching("fabric.mod.json") {
        expand("version" to version)
    }
}