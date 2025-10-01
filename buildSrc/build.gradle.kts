plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

kotlin {
    jvmToolchain(8)
}