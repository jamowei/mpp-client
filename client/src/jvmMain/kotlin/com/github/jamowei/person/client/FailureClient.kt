package com.github.jamowei.person.client

import com.github.jamowei.common.client.Protocol
import io.ktor.client.*
import io.ktor.client.engine.java.*

open class FailureClient(
    override val protocol: Protocol = Protocol.HTTP,
    override val host: String,
    override val port: Int
) : FailureClientDefinition() {
    override val client: HttpClient = HttpClient(Java, defaultClientConfig)
}