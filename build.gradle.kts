plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.spotless)
    alias(libs.plugins.publishdata)
}

group = "de.chojo"
version = "1.0.5"

repositories {
    mavenCentral()
}

dependencies {
    api(platform(libs.jackson.bom))
    api(libs.jackson.databind)
    api(libs.jackson.jsr310)
    api(libs.bucket4j)
    api(libs.httpcore5)
    api(libs.slf4j.api)
    compileOnlyApi(libs.annotations)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.launcher)
}

spotless {
    java {
        licenseHeaderFile(rootProject.file("HEADER.txt"))
        target("src/**/*.java")
    }
}

publishData {
    useEldoNexusRepos()
    publishComponent("java")
}

publishing {
    publications.create<MavenPublication>("maven") {
        publishData.configurePublication(this)
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
