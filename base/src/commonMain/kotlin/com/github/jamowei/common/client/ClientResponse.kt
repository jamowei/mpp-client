package com.github.jamowei.common.client

import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.charsets.*

class ClientResponse<T : Any>(
    val statusCode: HttpStatusCode,
    val headers: Headers,
    serviceResponse: ServiceResponse<T>
) {
    val severity: Severity = serviceResponse.severity
    val message: String = serviceResponse.message
    val content: T? = serviceResponse.content
}

suspend inline fun <reified T : Any> HttpResponse.toClientResponse(charset: Charset = Charsets.UTF_8) =
    ClientResponse<T>(this.status, this.headers, ServiceResponse.fromJson(this.bodyAsText(charset)))

suspend inline fun <reified T : Any> ResponseException.toClientResponse(charset: Charset = Charsets.UTF_8) =
    ClientResponse<T>(
        this.response.status,
        this.response.headers,
        ServiceResponse.fromJson(this.response.bodyAsText(charset))
    )
