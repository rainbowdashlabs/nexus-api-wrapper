plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.spotless)
    alias(libs.plugins.publishdata)
}

description = "Java client for the REST API of Sonatype Nexus Repository 3."

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 21
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).links("https://docs.oracle.com/en/java/javase/21/docs/api/")
}

dependencies {
    api(platform(libs.jackson.bom))
    api(libs.jackson.databind)
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
        target("src/**/*.java")
        licenseHeaderFile(rootProject.file("HEADER.txt"))
        palantirJavaFormat(libs.versions.palantir.get()).formatJavadoc(false)
        removeUnusedImports()
        importOrder("", "java", "javax", "\\#")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

publishData {
    eldoNexus(dev = false)
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        pom {
            url = "https://github.com/rainbowdashlabs/nexus-api-wrapper"
            inceptionYear = "2023"
            licenses {
                license {
                    name = "LGPL-3.0-or-later"
                    url = "https://www.gnu.org/licenses/lgpl-3.0.html"
                }
            }
            developers {
                developer {
                    id = "rainbowdashlabs"
                    name = "Nora Fülling"
                    email = "mail@chojo.dev"
                    url = "https://github.com/rainbowdashlabs"
                }
            }
            scm {
                url = "https://github.com/rainbowdashlabs/nexus-api-wrapper"
                connection = "scm:git:git://github.com/rainbowdashlabs/nexus-api-wrapper.git"
                developerConnection = "scm:git:ssh://github.com/rainbowdashlabs/nexus-api-wrapper.git"
            }
        }
    }
}

tasks.test {
    useJUnitPlatform {
        excludeTags("live")
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.register<Test>("liveTest") {
    description = "Runs the tests against eldonexus.de."
    group = "verification"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    useJUnitPlatform {
        includeTags("live")
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
}
