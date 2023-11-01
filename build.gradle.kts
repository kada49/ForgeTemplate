plugins {
    kotlin("jvm")
    id("gg.essential.multi-version")
    id("gg.essential.defaults")
}

val modGroup: String by project
val modBaseName: String by project
val modVersion: String by project

group = modGroup
version = modVersion
base.archivesName.set(modBaseName)

loom {
    runConfigs {
        getByName("client") {
            programArgs("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
        }
    }
}

//Creates a configuration called `embed` to declare dependencies
val embed: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    //With ´include´ you include libraries to be inside your .jar file.
    embed("gg.essential:loader-launchwrapper:1.2.2")
    //With ´implementation´ you include libraries NOT to be inside your .jar file.
    compileOnly("gg.essential:essential-$platform:14563+g7d1e613e0b")
}

tasks.jar {
    from(embed.files.map { zipTree(it) })

    //Manifest attributes to make Essential work as a mod
    manifest.attributes(
        mapOf(
            "ModSide" to "CLIENT",
            "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker"
        )
    )
}