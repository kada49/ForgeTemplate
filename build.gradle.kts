plugins {
    java
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("net.minecraftforge.gradle.forge") version "6f53277"
}

version = "1.0"
group = "dev.asbyth"

minecraft {
    version = "1.8.9-11.15.1.2318-1.8.9"
    runDir = "run"
    mappings = "stable_22"
    makeObfSourceJar = false
}

// Creates an extra configuration that implements `implementation` to be used later as the configuration that shades libraries
val include: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(include)

tasks.compileJava {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"

    options.encoding = "UTF-8"
}

repositories {
    // Add maven repositories to your buildscript so that they can be used to resolve dependencies
    // maven "https://repo1.maven.org/maven2/"
}

dependencies {
    // How to normally add a dependency (If you don't want it to be added to the jar)
    // implementation "com.example:example:1.0.0"
    // If you would like to include it (have the library inside your jar) instead use
    // include "com.example:example:1.0.0"
}

/**
 * This task simply replaces the `${version}` and `${mcversion}` properties in the mcmod.info with the data from Gradle
 */
tasks.processResources {
    // this will ensure that this task is redone when the versions change.
    inputs.property("version", project.version)
    inputs.property("mcversion", project.minecraft.version)

    // replace stuff in mcmod.info, nothing else
    from(sourceSets.main.get().resources.srcDirs) {
        include("mcmod.info")

        // replace version and mcversion
        expand("version" to project.version, "mcversion" to project.minecraft.version)
    }

    // copy everything else, thats not the mcmod.info
    from(sourceSets.main.get().resources.srcDirs) {
        exclude("mcmod.info")
    }
}

/**
 * This simply moves resources so they can be accessed at runtime, Forge is quite weird isn't it
 */
sourceSets {
    main {
        output.setResourcesDir(java.outputDir)
    }
}

// This adds support to ("embed", "shade", "include") libraries into our JAR
tasks.shadowJar {
    archiveClassifier.set("")
    archiveBaseName.set("ForgeTemplate")
    configurations = listOf(include)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

reobf {
    register("shadowJar") {}
}

tasks.jar {
    dependsOn(tasks.shadowJar)
}
tasks.jar.get().enabled = false