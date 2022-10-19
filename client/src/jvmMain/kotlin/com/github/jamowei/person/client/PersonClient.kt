package com.github.jamowei.person.client

import com.github.jamowei.common.client.ClientResponse
import com.github.jamowei.common.client.Protocol
import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.*
import io.ktor.http.*

class PersonClient(
    override val protocol: Protocol = Protocol.HTTP,
    override val host: String = "person-service.github.com",
    override val port: Int = 80,
    private val custom: HttpMessageBuilder.() -> Unit
) : PersonClientBase() {

    override val client: HttpClient = HttpClient(Java, defaultClientConfig)

    suspend fun customHeader(): ClientResponse<String> =
        client.perform {
            get("$origin/api/header") {
                requestConfig.also { custom() }()
            }
        }
}