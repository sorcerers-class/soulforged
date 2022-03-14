rootProject.name = "soulforged"
pluginManagement {
    repositories {
        jcenter()
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }

    plugins {
        id("fabric-loom") version "0.10-SNAPSHOT"
        id("org.jetbrains.kotlin.jvm") version "1.5.0"
    }
}