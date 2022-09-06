plugins {
    java
    kotlin("jvm") version "1.7.20-Beta"
    `maven-publish`
    alias(libs.plugins.quilt.loom)
}

val archivesBaseName = property("archives_base_name")!!
group = property("maven_group")!!
version = property("version")!!

repositories {
    mavenCentral()
    maven("https://maven.shedaniel.me/")
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${libs.versions.quilt.mappings.get()}:v2"))
    })
    modImplementation(libs.quilt.loader)
    modImplementation(libs.quilted.fabric.api)
    val imguiVersion = "1.86.4"
    implementation("io.github.spair:imgui-java-binding:$imguiVersion")
    implementation("io.github.spair:imgui-java-lwjgl3:$imguiVersion") {
        exclude(group = "org.lwjgl")
    }
    // Apache common collections because why not
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}
loom {
    this.accessWidenerPath.set {
        file("src/main/resources/soulforged.accesswidener")
    }
}
tasks {

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE")
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifact(remapJar) {
                    builtBy(remapJar)
                }
                artifact(kotlinSourcesJar) {
                    builtBy(remapSourcesJar)
                }
            }
        }

        // select the repositories you want to publish to
        repositories {
            // uncomment to publish to the local maven
            // mavenLocal()
        }
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "18"
    }

}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}