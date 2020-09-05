plugins {
    id("org.jetbrains.dokka") version "1.4.0-rc"
    `maven-publish`
}

base {
    archivesBaseName = rootProject.name
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.9")
}

kotlin {
    js(BOTH) {
        browser()
        nodejs()
    }
}

tasks {
    dokkaHtml.configure {
        outputDirectory = "$buildDir/javadoc"
    }

    publishToMavenLocal {
        dependsOn(build)
    }
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
    dependsOn(tasks.dokkaHtml)
}

val pomUrl = "https://github.com/znerol/kotlinx-coroutines-observable-js"
val pomScmUrl = "https://github.com/znerol/kotlinx-coroutines-observable-js"
val pomIssueUrl = "https://github.com/znerol/kotlinx-coroutines-observable-js/issues"
val pomDesc = "Kotlin flow compatibility with TC39 observable"
val pomScmConnection = "scm:git:git://github.com/znerol/kotlinx-coroutines-observable-js"
val pomScmDevConnection = "scm:git:git://github.com/znerol/kotlinx-coroutines-observable-js"

val pomLicenseName = "The Apache Software License, Version 2.0"
val pomLicenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
val pomLicenseDist = "repo"

val pomDeveloperId = "znerol"
val pomDeveloperName = "znerol"

publishing {
    publications {
        create<MavenPublication>("lib") {
            groupId = rootProject.group.toString()
            artifactId = rootProject.name
            version = rootProject.version.toString()
            from(components["kotlin"])
            artifact(tasks["dokkaJar"])
            artifact(tasks["kotlinSourcesJar"])
            pom.withXml {
                asNode().apply {
                    appendNode("description", pomDesc)
                    appendNode("name", rootProject.name)
                    appendNode("url", pomUrl)
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", pomLicenseName)
                        appendNode("url", pomLicenseUrl)
                        appendNode("distribution", pomLicenseDist)
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", pomDeveloperId)
                        appendNode("name", pomDeveloperName)
                    }
                    appendNode("scm").apply {
                        appendNode("url", pomScmUrl)
                        appendNode("connection", pomScmConnection)
                    }
                }
            }
        }
    }

    repositories {
        maven {
            val org = "znerol"
            val repo = "kotlinx"
            val name = "kotlinx-coroutines-observable-js"

            url = uri("https://api.bintray.com/maven/$org/$repo/$name/;publish=1")

            credentials {
                username = System.getenv("BINTRAY_USER")
                password = System.getenv("BINTRAY_API_KEY")
            }
        }
    }
}
