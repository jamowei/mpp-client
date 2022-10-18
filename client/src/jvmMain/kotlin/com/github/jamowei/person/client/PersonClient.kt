package com.github.jamowei.person.client

import com.github.jamowei.common.client.Port
import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.http.*

class PersonClient(
    override val host: String = "person-service.person",
    override val port: Port = Port.HTTP
) : PersonClientBase() {
    override var header: HeadersBuilder.() -> Unit = {}
    override val client: HttpClient = HttpClient(Java, clientConfig)
}