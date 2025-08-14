plugins {
    kotlin("jvm") version "2.2.0"
    `java-library`
    `maven-publish`
}

group = "com.shahid"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val ktor_version: String by project

dependencies {
    testImplementation(kotlin("test"))
    api("io.ktor:ktor-client-core:${ktor_version}")
    api("io.ktor:ktor-client-cio:${ktor_version}")
    api("io.ktor:ktor-client-content-negotiation:${ktor_version}")
    api("io.ktor:ktor-serialization-kotlinx-json:${ktor_version}")
    api("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.x.x")
    api("io.ktor:ktor-serialization-jackson:${ktor_version}")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/apearson75/TFLBusesLib")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("maven") {
            groupId = "com.shahid"
            version = version
            artifactId = "tflbuseslib"
            from(components["java"])
        }
    }
}