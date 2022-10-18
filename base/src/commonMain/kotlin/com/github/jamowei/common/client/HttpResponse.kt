package com.github.jamowei.common.client

import io.ktor.http.*

class HttpResponse<T : Any>(
    val statusCode: HttpStatusCode,
    val body: T
)