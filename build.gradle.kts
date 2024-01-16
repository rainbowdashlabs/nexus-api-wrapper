plugins {
    id("java")
    `java-library`
    `maven-publish`
    id("com.diffplug.spotless") version "6.24.0"
    id("de.chojo.publishdata") version "1.3.0"
}

group = "de.chojo"
version = "1.0.0"

repositories {
    mavenCentral()
}


dependencies {
    // Logging
    api("org.slf4j", "slf4j-api", "2.0.11")

    api("com.fasterxml.jackson.core", "jackson-databind", "2.16.1")
    api("com.bucket4j", "bucket4j-core", "8.7.0")
    api("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")
    api("org.apache.httpcomponents.client5", "httpclient5", "5.2.1")

    // code
    api("org.jetbrains", "annotations", "24.1.0")
    api("com.google.code.findbugs", "jsr305", "3.0.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
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
