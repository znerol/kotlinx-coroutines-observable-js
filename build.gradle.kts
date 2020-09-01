plugins {
    id("org.jetbrains.kotlin.js") version "1.4.0"
}

group = "ch.znerol.kotlinx"
version = "1.0.0"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    testImplementation(npm("zen-observable", "0.8.15", generateExternals = true))
    testImplementation(npm("rxjs", "6.6.2", generateExternals = true))
}

kotlin {
    js(BOTH) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
}