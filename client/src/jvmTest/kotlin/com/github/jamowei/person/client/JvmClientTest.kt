package com.github.jamowei.person.client

import com.github.jamowei.common.client.NoServiceResponseException
import com.github.jamowei.common.client.NotFoundException
import com.github.jamowei.common.client.Protocol
import com.github.jamowei.common.client.Severity
import com.github.jamowei.person.model.Person
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.net.SocketException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class JvmClientTest {

    private val personClient = PersonClient(Protocol.HTTP, "localhost", 3000)
    private val headerClient = HeaderClient(Protocol.HTTP, "localhost", 3000)
    private val failureClient = FailureClient(Protocol.HTTP, "localhost", 3000)
    private val person = Person("Peter", 45, "peter@schwarz.de", 1.85)

    @Test
    fun testSaveAndLoad() {
        runBlocking {
            val save = personClient.save(person)
            println(save.message)
            assertEquals(HttpStatusCode.OK, save.statusCode)
            assertEquals(Severity.Info, save.severity)
            assertEquals(person, save.content)

            val load = personClient.load(person.name)
            println(load.message)
            assertEquals(HttpStatusCode.OK, load.statusCode)
            assertEquals(Severity.Info, load.severity)
            assertEquals(person, load.content)
        }
    }

    @Test
    fun testLoadNotFound() {
        runBlocking {
            val response = personClient.load("foo")
            println(response.message)
            assertEquals(HttpStatusCode.NotFound, response.statusCode)
            assertEquals(Severity.Error, response.severity)
            assertEquals(null, response.content)
        }
    }

    @Test
    fun testCustomHeader() {
        runBlocking {
            val headerValue = "Hello World!"
            val response = headerClient.customHeader(headerValue)
            println(response.message)
            assertEquals(HttpStatusCode.OK, response.statusCode)
            assertEquals(Severity.Info, response.severity)
            assertEquals(headerValue, response.content)
        }
    }

    @Test
    fun testNoServiceResponseException() {
        runBlocking {
            assertFailsWith(NoServiceResponseException::class) {
                failureClient.noServiceResponse()
            }
        }
    }

    @Test
    fun testNotFoundException() {
        runBlocking {
            assertFailsWith(NotFoundException::class) {
                failureClient.noConnection()
            }
        }
    }

    @Test
    fun testNoConnection() {
        runBlocking {
            assertFailsWith(SocketException::class) {
                val client = object : FailureClient(Protocol.HTTP, "localhost", 3333) {}
                client.noConnection()
            }
        }
    }
}