package com.github.jamowei.server

import com.github.jamowei.common.client.ServiceResponse
import com.github.jamowei.common.client.Severity
import com.github.jamowei.person.model.Person
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
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

        install(CORS) { // needed for js-client
            anyHost()
            allowHeader(HttpHeaders.ContentType)
            allowHeader("Custom-Header")
        }


        routing {
            route("/api") {
                get("/person/{name}") {
                    call.parameters["name"]?.let { name ->
                        memory[name]?.let {
                            call.respond(ServiceResponse(Severity.Info, "Person found", it))
                        }
                    } ?: call.respond(
                        HttpStatusCode.NotFound,
                        ServiceResponse<Unit>(Severity.Error, "Person not found")
                    )
                }
                post("/person") {
                    call.receiveNullable<Person>()?.let {
                        memory[it.name] = it
                        call.respond(HttpStatusCode.OK, ServiceResponse(Severity.Info, "Person saved successfully", it))
                    } ?: call.respond(
                        HttpStatusCode.BadRequest,
                        ServiceResponse<Unit>(Severity.Error, "No Person in request")
                    )
                }
                get("/header") {
                    call.request.headers["Custom-Header"]?.let {
                        call.respond(
                            HttpStatusCode.OK,
                            ServiceResponse(Severity.Info, "Custom header read successfully", it)
                        )
                    } ?: call.respond(
                        HttpStatusCode.BadRequest,
                        ServiceResponse<Unit>(Severity.Error, "No custom header in request")
                    )
                }
            }
        }
    }.start(wait = true)
}