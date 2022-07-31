pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        //Repositories needed for the essential-gradle-toolkit to fetch Minecraft's dependencies
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
    }
    plugins {
        val egtVersion = "0.1.10"
        id("gg.essential.multi-version.root") version egtVersion
    }
}

rootProject.buildFileName = "root.gradle.kts"
rootProject.name = "ForgeTemplate"

val mcVersion = "1.8.9"

include(":$mcVersion")
project(":$mcVersion").apply {
    projectDir = file("versions/$mcVersion")
    buildFileName = "../../build.gradle.kts"
}