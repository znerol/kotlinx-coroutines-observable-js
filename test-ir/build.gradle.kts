plugins {
    id("org.jetbrains.kotlin.js") version "1.4.0"
}

group = "ch.znerol.kotlinx"
version = "1.0.0"

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation(project(":lib"))
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
}
