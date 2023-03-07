plugins {
    id("java")
    `java-library`
    `maven-publish`
}

group = "de.chojo"
version = "1.0-SNAPSHOT"

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
}

tasks{
            test {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
        }

}
