@file:Suppress("DSL_SCOPE_VIOLATION", "MISSING_DEPENDENCY_CLASS", "FUNCTION_CALL_EXPECTED", "PropertyName")

plugins {
    java
    `maven-publish`

    alias(libs.plugins.kotlin)
    alias(libs.plugins.quilt.loom)
}

val archives_base_name = property("archives_base_name")!!
group = property("maven_group")!!
version = property("version")!!

val javaVersion = 17

repositories {
    mavenCentral()
    maven("https://maven.shedaniel.me/")
}

dependencies {
    minecraft(libs.minecraft)
    mappings(
        variantOf(libs.quilt.mappings) {
            classifier("intermediary-v2")
        }
    )
    modImplementation(libs.quilt.loader)
    modImplementation(libs.qfapi)
    modImplementation(libs.qkl) {
        exclude("org.quiltmc", "quilted-fabric-api")
        exclude("org.quiltmc", "qsl")
    }
    implementation(libs.bundles.imgui) {
        exclude(group = "org.lwjgl")
    }
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("commons-codec:commons-codec:1.15")

}
loom {
    this.accessWidenerPath.set {
        file("src/main/resources/soulforged.accesswidener")
    }
}
tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
            // languageVersion: A.B of the kotlin plugin version A.B.C
            languageVersion = libs.plugins.kotlin.get().version.requiredVersion.substringBeforeLast('.')
        }
    }

    withType<JavaCompile>.configureEach {
        options.encoding = "UTF-8"
        options.isDeprecation = true
        options.release.set(javaVersion)
    }

    processResources {
        filteringCharset = "UTF-8"
        inputs.property("version", project.version)

        filesMatching("quilt.mod.json") {
            expand(
                mapOf(
                    "version" to project.version
                )
            )
        }
    }

    javadoc {
        options.encoding = "UTF-8"
    }

    // Run `./gradlew wrapper --gradle-version <newVersion>` or `gradle wrapper --gradle-version <newVersion>` to update gradle scripts
    // BIN distribution should be sufficient for the majority of mods
    wrapper {
        distributionType = Wrapper.DistributionType.BIN
    }

    jar {
        from("LICENSE") {
            rename { "LICENSE_${archives_base_name}" }
        }
    }
}

val targetJavaVersion = JavaVersion.toVersion(javaVersion)
if (JavaVersion.current() < targetJavaVersion) {
    kotlin.jvmToolchain(javaVersion)

    java.toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    // If this mod is going to be a library, then it should also generate Javadocs in order to aid with development.
    // Uncomment this line to generate them.
    // withJavadocJar()

    // Still required by IDEs such as Eclipse and VSC
    sourceCompatibility = targetJavaVersion
    targetCompatibility = targetJavaVersion
}

// Configure the maven publication
publishing {
    publications {
        register<MavenPublication>("Maven") {
            from(components.getByName("java"))
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}