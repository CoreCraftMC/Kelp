import java.util.*

pluginManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public")

        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "Kelp"

for (name in listOf("Kelp-API", "Kelp-Common", "Kelp-Server", "paper-api-generator")) {
    val projName = name.lowercase(Locale.ENGLISH)
    include(projName)
    findProject(":$projName")!!.projectDir = file(name)
}