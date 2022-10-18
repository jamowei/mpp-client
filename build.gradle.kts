plugins {
    kotlin("multiplatform") version "1.7.20" apply false
    kotlin("jvm") version "1.7.20" apply false
    kotlin("plugin.serialization") version "1.7.20"
}

group = "com.github.jamowei"
version = "0.0.0"

allprojects {
    repositories {
        mavenCentral()
    }
}