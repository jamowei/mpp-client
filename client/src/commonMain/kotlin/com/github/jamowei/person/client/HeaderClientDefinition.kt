package com.github.jamowei.person.client

import com.github.jamowei.common.client.ClientDefinition
import com.github.jamowei.common.client.ClientResponse
import io.ktor.client.request.*

abstract class HeaderClientDefinition : ClientDefinition() {

    private val endpoint = "api/header"

    suspend fun customHeader(headerValue: String): ClientResponse<String> =
        client.perform {
            get("$origin/$endpoint") {
                defaultHttpConfig.also {
                    headers {
                        set("Custom-Header", headerValue)
                    }
                }()
            }
        }
}