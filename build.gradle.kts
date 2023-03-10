import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    `java-library`
    `maven-publish`
    id("com.diffplug.spotless") version "6.16.0"
    id("de.chojo.publishdata") version "1.1.0"
    kotlin("jvm") version "1.8.20-Beta"
}

group = "de.chojo"
version = "1.0.5"

repositories {
    mavenCentral()
}


dependencies {
    // Logging
    api("org.slf4j", "slf4j-api", "2.0.3")

    api("com.fasterxml.jackson.core", "jackson-databind", "2.13.4")
    api("com.bucket4j", "bucket4j-core", "8.2.0")
    api("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")
    api("org.apache.httpcomponents.client5", "httpclient5", "5.1.3")

    // code
    api("org.jetbrains", "annotations", "23.0.0")
    api("com.google.code.findbugs", "jsr305", "3.0.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation(kotlin("stdlib-jdk8"))
  testImplementation("com.google.guava:guava:31.1-jre")
}

spotless {
    java {
        licenseHeaderFile(rootProject.file("HEADER.txt"))
        target("**/*.java")
    }
}

publishData {
    useEldoNexusRepos()
    publishComponent("java")
}

publishing {
    publications.create<MavenPublication>("maven") {
        publishData.configurePublication(this)
        pom {
            url.set("https://github.com/rainbowdashlabs/universalis-java")
            developers {
                developer {
                    name.set("Florian Fülling")
                    url.set("https://github.com/rainbowdashlabs")
                }
            }
            licenses {
                license {
                    name.set("GNU Affero General Public License v3.0")
                    url.set("https://github.com/rainbowdashlabs/universalis-java/blob/main/LICENSE.md")
                }
            }
        }
    }

    repositories {
        maven {
            authentication {
                credentials(PasswordCredentials::class) {
                    username = System.getenv("NEXUS_USERNAME")
                    password = System.getenv("NEXUS_PASSWORD")
                }
            }

            setUrl(publishData.getRepository())
            name = "EldoNexus"
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
