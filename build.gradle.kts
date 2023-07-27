import dev.architectury.pack200.java.Pack200Adapter

plugins {
    java
    kotlin("jvm") version ("1.8.22")
    id("gg.essential.loom") version ("1.2.8")
    id("dev.architectury.architectury-pack200") version ("0.1.3")
}

val modGroup: String by project
val modName: String by project
val modVersion: String by project

group = modGroup
version = modVersion
base.archivesName.set(modName)

loom {
    // Uncomment this if you want to use the essential library
    /*
    runConfigs.getByName("client") {
        programArgs("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
    }
    */

    forge.pack200Provider.set(Pack200Adapter())
}

repositories {
    maven("https://repo.essential.gg/repository/maven-public")
}

//Creates a configuration called `include` to declare dependencies
val embed: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    // Dependencies required for loom to set up the development environment
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

    // Uncomment the following lines if you want to use the essential library
    embed("gg.essential:loader-launchwrapper:1.2.1")
    compileOnly("gg.essential:essential-1.8.9-forge:13419+gad15d412e")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("mcmod.info") {
            expand("version" to project.version)
        }
    }

    jar {
        // Includes dependencies added with 'include' in the final .jar mod file
        from(embed.files.map { zipTree(it) })

        // Uncomment this if you want to use the essential library
        /*
        manifest.attributes(
            mapOf(
                "ModSide" to "CLIENT",
                "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker"
            )
        )
        */
    }

    withType<JavaCompile> {
        options.release.set(8)
        options.encoding = "UTF-8"
    }
}