package com.github.jamowei.common.client

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

abstract class ClientBase {
    abstract val protocol: Protocol
    abstract val host: String
    abstract val port: Int

    protected abstract val client: HttpClient

    protected open val requestConfig: HttpMessageBuilder.() -> Unit = {
        contentType(ContentType.Application.Json)
    }

    protected val defaultClientConfig: HttpClientConfig<HttpClientEngineConfig>.() -> Unit = {
        install(ContentNegotiation) { json(Json) }
    }


    suspend inline fun <reified T : Any> HttpClient.perform(block: HttpClient.() -> HttpResponse): ClientResponse<T> =
        runCatching { block().toClientResponse<T>() }.getOrElse {
            when (it) {
                is ResponseException -> it.toClientResponse<T>()
                else -> throw it
            }
        }

    val origin: String get() = "${protocol.name.lowercase()}://$host:$port"

    override fun toString(): String = "HttpClient ($origin)"
}