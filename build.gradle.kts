plugins {
    kotlin("js") version "1.4.0" apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.js")
}

allprojects {
    group = "ch.znerol.kotlinx"
    version = "1.0.0"

    repositories {
        jcenter()
    }
}
