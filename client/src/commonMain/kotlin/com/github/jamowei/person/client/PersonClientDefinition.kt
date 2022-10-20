package com.github.jamowei.person.client

import com.github.jamowei.common.client.ClientDefinition
import com.github.jamowei.common.client.ClientResponse
import com.github.jamowei.person.model.Person
import io.ktor.client.request.*

abstract class PersonClientDefinition : ClientDefinition() {

    private val endpoint = "api/person"

    suspend fun save(person: Person): ClientResponse<Person> =
        client.perform {
            post("$origin/$endpoint") {
                defaultHttpConfig()
                setBody(person)
            }
        }

    suspend fun load(name: String): ClientResponse<Person> =
        client.perform {
            get("$origin/$endpoint/$name") {
                defaultHttpConfig()
            }
        }
}