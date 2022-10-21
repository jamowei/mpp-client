package com.github.jamowei.person.client

import com.github.jamowei.common.client.ClientDefinition
import com.github.jamowei.common.client.ClientResponse
import io.ktor.client.request.*

abstract class FailureClientDefinition : ClientDefinition() {

    private val endpoint = "api/no-service-response"

    suspend fun noServiceResponse(): ClientResponse<String> =
        client.perform {
            get("$origin/$endpoint") {
                defaultHttpConfig()
            }
        }

    suspend fun noConnection(): ClientResponse<Unit> =
        client.perform {
            get("$origin/not-exists") {
                defaultHttpConfig()
            }
        }
}