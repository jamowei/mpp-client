package com.github.jamowei.person.client

import com.github.jamowei.common.client.Protocol
import io.ktor.client.*
import io.ktor.client.engine.java.*

class PersonClient(
    override val protocol: Protocol = Protocol.HTTP,
    override val host: String = "person-service.github.com",
    override val port: Int = 80
) : PersonClientDefinition() {
    override val client: HttpClient = HttpClient(Java, defaultClientConfig)
}