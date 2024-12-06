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
    jvmToolchain(15)
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
}