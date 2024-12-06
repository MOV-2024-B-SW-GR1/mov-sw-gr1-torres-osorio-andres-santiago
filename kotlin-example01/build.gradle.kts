plugins {
    kotlin("jvm") version "2.0.20"
}

group = "ec.epn.edu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(15)) // Cambia 15 por la versión de tu JRE
    }
}