import gg.essential.gradle.util.noServerRunConfigs

plugins {
    kotlin("jvm")
    id("gg.essential.multi-version")
    id("gg.essential.defaults")
}

val modGroup: String by project
val modBaseName: String by project
group = modGroup
base.archivesName.set(modBaseName)
version = "1.0"

loom {
    noServerRunConfigs()
    launchConfigs {
        getByName("client") {
            arg("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
        }
    }
}

//Creates a configuration called `include` to declare dependencies
val include: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(include)

dependencies {
    //With ´include´ you include libraries to be inside your .jar file.
    include("gg.essential:loader-launchwrapper:1.1.3")
    //With ´implementation´ you include libraries NOT to be inside your .jar file.
    implementation("gg.essential:essential-$platform:4276+g845a16235")
}

tasks.jar {
    from(include.files.map { zipTree(it) })

    //Manifest attributes to make Essential work as a mod
    manifest.attributes(
        mapOf(
            "ModSide" to "CLIENT",
            "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker"
        )
    )
}