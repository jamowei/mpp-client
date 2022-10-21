package com.github.jamowei.common.client

import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.charsets.*

class ClientResponse<T : Any>(
    val method: HttpMethod,
    val url: Url,
    val statusCode: HttpStatusCode,
    val headers: Headers,
    val version: HttpProtocolVersion,
    serviceResponse: ServiceResponse<T>
) {
    val severity: Severity = serviceResponse.severity
    val message: String = serviceResponse.message
    val content: T? = serviceResponse.content
}

suspend inline fun <reified T : Any> HttpResponse.toClientResponse(charset: Charset = Charsets.UTF_8) =
    try {
        ClientResponse<T>(
            this.request.method,
            this.request.url,
            this.status,
            this.headers,
            this.version,
            ServiceResponse.fromJson(this.bodyAsText(charset))
        )
    } catch (cause: Throwable) {
        when (this.status) {
            HttpStatusCode.NotFound -> throw NotFoundException(
                "[${this.request.method.value}] [${this.status.value} ${this.status.description}] ${this.request.url}: " +
                        "endpoint is not available", cause
            )

            else -> throw NoServiceResponseException(
                "[${this.request.method.value}] [${this.status.value} ${this.status.description}] ${this.request.url}: " +
                        "HttpResponse could not be parsed to a ClientResponse. Sure you use ServiceResponse class on server side?",
                cause
            )
        }
    }

suspend inline fun <reified T : Any> ResponseException.toClientResponse(charset: Charset = Charsets.UTF_8) =
    try {
        ClientResponse<T>(
            this.response.request.method,
            this.response.request.url,
            this.response.status,
            this.response.headers,
            this.response.version,
            ServiceResponse.fromJson(this.response.bodyAsText(charset))
        )
    } catch (cause: Throwable) {
        when (this.response.status) {
            HttpStatusCode.NotFound -> throw NotFoundException(
                "[${this.response.request.method.value}] [${this.response.status.value} ${this.response.status.description}] ${this.response.request.url}: " +
                        "endpoint is not available", cause
            )

            else -> throw NoServiceResponseException(
                "[${this.response.request.method.value}] [${this.response.status.value} ${this.response.status.description}] ${this.response.request.url}: " +
                        "HttpResponse could not be parsed to a ClientResponse. Sure you use ServiceResponse class on server side?",
                cause
            )
        }
    }