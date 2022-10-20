package com.github.jamowei.person.client

import com.github.jamowei.common.client.Protocol
import com.github.jamowei.common.client.Severity
import com.github.jamowei.person.model.Person
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.promise
import kotlin.test.Test
import kotlin.test.assertEquals

class JsClientTest {

    private val personClient = PersonClient(Protocol.HTTP, "localhost", 3000)
    private val headerClient = HeaderClient(Protocol.HTTP, "localhost", 3000)

    private val person = Person("Hans", 45, "peter@schwarz.de", 1.85)

    @Test
    fun testSaveAndLoad() = runTest {
        val save = personClient.save(person)
        println(save.message + "\n")
        assertEquals(HttpStatusCode.OK, save.statusCode)
        assertEquals(Severity.Info, save.severity)
        assertEquals(person, save.content)

        val load = personClient.load(person.name)
        println(load.message + "\n")
        assertEquals(HttpStatusCode.OK, load.statusCode)
        assertEquals(Severity.Info, load.severity)
        assertEquals(person, load.content)
    }

    @Test
    fun testLoadNotFound() = runTest {
        val response = personClient.load("foo")
        println(response.message + "\n")
        assertEquals(HttpStatusCode.NotFound, response.statusCode)
        assertEquals(Severity.Error, response.severity)
        assertEquals(null, response.content)
    }

    @Test
    fun testCustomHeader() = runTest {
        val headerValue = "Hello World!"
        val response = headerClient.customHeader(headerValue)
        println(response.message)
        assertEquals(HttpStatusCode.OK, response.statusCode)
        assertEquals(Severity.Info, response.severity)
        assertEquals(headerValue, response.content)
    }
}

fun <T> runTest(block: suspend CoroutineScope.() -> T): dynamic = MainScope().promise {
    delay(50)
    block()
    delay(50)
}