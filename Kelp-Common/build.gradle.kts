plugins {
    alias(libs.plugins.shadow)

    `java-plugin`
}

feather {
    repository("https://repo.crazycrew.us")
}

dependencies {
    implementation(libs.vital.core)
}