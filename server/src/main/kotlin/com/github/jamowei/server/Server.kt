package com.github.jamowei.server

import com.github.jamowei.person.model.Person
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.concurrent.ConcurrentHashMap

val memory: ConcurrentHashMap<String, Person> = ConcurrentHashMap()

fun main() {
    embeddedServer(Netty, port = 3000, host = "127.0.0.1") {

        install(ContentNegotiation) {
            json()
        }

        routing {
            get("/api/person/{name}") {
                call.parameters["name"]?.let { name ->
                    memory[name]?.let {
                        call.respond(it)
                    }
                } ?: call.respond(HttpStatusCode.NotFound)
            }
            post("/api/person") {
                val person = call.receive<Person>()
                memory[person.name] = person
                call.respond(HttpStatusCode.OK, person)
            }
        }
    }.start(wait = true)
}