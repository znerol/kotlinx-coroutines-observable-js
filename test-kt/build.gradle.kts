dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.9")
    implementation(project(":lib"))
}

kotlin {
    js() {
        browser()
        nodejs()
        binaries.executable()
    }
}
