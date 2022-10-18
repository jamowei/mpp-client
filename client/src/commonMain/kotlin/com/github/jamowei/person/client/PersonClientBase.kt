package com.github.jamowei.person.client

import com.github.jamowei.common.client.HttpResponse
import com.github.jamowei.common.client.Port
import com.github.jamowei.person.model.Person
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

abstract class PersonClientBase {

    abstract val host: String
    abstract val port: Port

    private val endpoint = "api/person"

    protected val clientConfig: HttpClientConfig<HttpClientEngineConfig>.() -> Unit = {
        install(ContentNegotiation) { json(Json) }
    }

    protected abstract val client: HttpClient

    protected open var header: HeadersBuilder.() -> Unit = {}

    suspend fun save(person: Person): HttpResponse<Person> {
        val response = client.post("http://$host:$port/$endpoint") {
            setBody(person)
            headers(header)
        }
        return HttpResponse(response.status, response.body())
    }

    suspend fun load(name: String): HttpResponse<Person> {
        val response = client.get("http://$host:$port/$endpoint/?name=$name") {
            headers(header)
        }
        return HttpResponse(response.status, response.body())
    }
}