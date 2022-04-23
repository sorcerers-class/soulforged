plugins {
    kotlin("jvm") version "1.5.10"
    alias(libs.plugins.quilt.loom)

    `maven-publish`
}
group = property("maven_group")!!
version = property("version")!!

repositories {
    mavenCentral()
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
}
fun DependencyHandlerScope.includeAndExpose(dep: Any) {
    modApi(dep)
    include(dep)
}
val kotlinVersion: String by project
val coroutinesVersion: String by project
val serializationVersion: String by project

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${libs.versions.quilt.mappings.get()}:v2"))
    })
    modImplementation(libs.quilt.loader)
    modImplementation(libs.quilted.fabric.api)

    includeAndExpose(kotlin("stdlib", kotlinVersion))
    includeAndExpose(kotlin("stdlib-jdk8", kotlinVersion))
    includeAndExpose(kotlin("stdlib-jdk7", kotlinVersion))
    includeAndExpose(kotlin("reflect", kotlinVersion))
    includeAndExpose("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    includeAndExpose("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$coroutinesVersion")
    includeAndExpose("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")
    includeAndExpose("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:$serializationVersion")
    includeAndExpose("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:$serializationVersion")
    includeAndExpose("org.jetbrains.kotlinx:kotlinx-serialization-cbor-jvm:$serializationVersion")

    val imguiVersion = "1.86.4"
    implementation("io.github.spair:imgui-java-binding:$imguiVersion")
    implementation("io.github.spair:imgui-java-lwjgl3:$imguiVersion") {
        exclude(group = "org.lwjgl")
    }
    // Apache common collections because why not
    implementation("org.apache.commons:commons-collections4:4.4")
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
        filesMatching("quilt.mod.json") {
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
        kotlinOptions.jvmTarget = "16"
    }

}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}