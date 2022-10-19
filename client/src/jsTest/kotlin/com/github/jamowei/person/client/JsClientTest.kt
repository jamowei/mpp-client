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

    private val client = PersonClient(Protocol.HTTP, "localhost", 3000)
    private val person = Person("Hans", 45, "peter@schwarz.de", 1.85)

    @Test
    fun testSaveAndLoad() = runTest {
        val save = client.save(person)
        println(save.message + "\n")
        assertEquals(HttpStatusCode.OK, save.statusCode)
        assertEquals(Severity.Info, save.severity)
        assertEquals(person, save.content)

        val load = client.load(person.name)
        println(load.message + "\n")
        assertEquals(HttpStatusCode.OK, load.statusCode)
        assertEquals(Severity.Info, load.severity)
        assertEquals(person, load.content)
    }

    @Test
    fun testLoadNotFound() = runTest {
        val response = client.load("foo")
        println(response.message + "\n")
        assertEquals(HttpStatusCode.NotFound, response.statusCode)
        assertEquals(Severity.Error, response.severity)
        assertEquals(null, response.content)
    }

}

fun <T> runTest(block: suspend CoroutineScope.() -> T): dynamic = MainScope().promise {
    delay(50)
    block()
    delay(50)
}