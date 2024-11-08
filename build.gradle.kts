import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    alias(libs.plugins.paperweight.patcher)

    `maven-publish`
    `java-library`
}

val paperMavenPublicUrl = "https://repo.papermc.io/repository/maven-public/"

repositories {
    maven(paperMavenPublicUrl) {
        content {
            onlyForConfigurations(configurations.paperclip.name)
        }
    }

    mavenCentral()
}

allprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    tasks {
        processResources {
            filteringCharset = Charsets.UTF_8.name()
        }

        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(21)
        }

        javadoc {
            options.encoding = Charsets.UTF_8.name()
        }

        test {
            testLogging {
                showStackTraces = true
                exceptionFormat = TestExceptionFormat.FULL
                events(TestLogEvent.STANDARD_OUT)
            }
        }
    }
}

subprojects {
    repositories {
        maven("https://repo.crazycrew.us")

        maven("https://jitpack.io")

        maven(paperMavenPublicUrl)

        mavenCentral()
    }
}

dependencies {
    remapper("net.fabricmc:tiny-remapper:0.10.3:fat")
    decompiler("org.vineflower:vineflower:1.10.1")
    paperclip("io.papermc:paperclip:3.0.3")
}

paperweight {
    serverProject = project(":kelp-server")

    remapRepo = paperMavenPublicUrl
    decompileRepo = paperMavenPublicUrl

    useStandardUpstream("purpur") {
        url = github("PurpurMC", "Purpur")
        ref = providers.gradleProperty("purpurCommit")

        withStandardPatcher {
            baseName("Purpur")

            apiPatchDir = layout.projectDirectory.dir("patches/api")
            apiOutputDir = layout.projectDirectory.dir("Kelp-API")

            serverPatchDir = layout.projectDirectory.dir("patches/server")
            serverOutputDir = layout.projectDirectory.dir("Kelp-Server")
        }

        patchTasks.register("generatedApi") {
            isBareDirectory = true
            upstreamDirPath = "paper-api-generator/generated"
            patchDir = layout.projectDirectory.dir("patches/generated-api")
            outputDir = layout.projectDirectory.dir("paper-api-generator/generated")
        }
    }
}

tasks.generateDevelopmentBundle {
    apiCoordinates = "me.corecraft.kelp:kelp-api"

    libraryRepositories.set(
        listOf(
            "https://repo.maven.apache.org/maven2/",
            paperMavenPublicUrl,
            "https://repo.purpurmc.org/snapshots",
        )
    )
}

tasks.register("printMinecraftVersion") {
    doLast {
        println(providers.gradleProperty("mcVersion").get().trim())
    }
}

tasks.register("printKelpVersion") {
    doLast {
        println(project.version)
    }
}