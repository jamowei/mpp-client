plugins {
    kotlin("multiplatform")
}

val ktorVersion: String by project

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
    }
}