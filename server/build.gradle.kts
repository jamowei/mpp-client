plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.psxpaul.execfork") version "0.2.0"
}

val ktorVersion: String by project
val serializationVersion: String by project
val logbackVersion: String by project

dependencies {
    implementation(project(":base"))
    implementation(project(":client"))
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("io.ktor:ktor-client-java:$ktorVersion")
}

application {
    mainClass.set("com.github.jamowei.server.ServerKt")
}

tasks {
    val start = register("startServer", com.github.psxpaul.task.JavaExecFork::class) {
        classpath = sourceSets.main.get().runtimeClasspath
        main = application.mainClass.get()
        waitForPort = 3000
        process?.pid()
    }
}