plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

val ktorVersion: String by project
val serializationVersion: String by project

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
            dependsOn(":server:startServer")
        }
    }
    js(IR) {
        browser {
            testRuns["test"].executionTask {
                dependsOn(":server:startServer")
            }
        }
    }.binaries.executable()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":base"))
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-java:$ktorVersion")
                implementation(kotlin("reflect"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}