import com.github.jamowei.common.client.Protocol
import com.github.jamowei.common.client.Severity
import com.github.jamowei.person.client.PersonClient
import com.github.jamowei.person.model.Person
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JvmClientTest {

    private val headerValue = "Hello World!"
    private val client = PersonClient(Protocol.HTTP, "localhost", 3000) {
        headers {
            set("Custom-Header", headerValue)
        }
    }
    private val person = Person("Peter", 45, "peter@schwarz.de", 1.85)

    @Test
    fun testSaveAndLoad() {
        runBlocking {
            val save = client.save(person)
            println(save.message)
            assertEquals(HttpStatusCode.OK, save.statusCode)
            assertEquals(Severity.Info, save.severity)
            assertEquals(person, save.content)

            val load = client.load(person.name)
            println(load.message)
            assertEquals(HttpStatusCode.OK, load.statusCode)
            assertEquals(Severity.Info, load.severity)
            assertEquals(person, load.content)
        }
    }

    @Test
    fun testLoadNotFound() {
        runBlocking {
            val response = client.load("foo")
            println(response.message)
            assertEquals(HttpStatusCode.NotFound, response.statusCode)
            assertEquals(Severity.Error, response.severity)
            assertEquals(null, response.content)
        }
    }

    @Test
    fun testCustomHeader() {
        runBlocking {
            val response = client.customHeader()
            println(response.message)
            assertEquals(HttpStatusCode.OK, response.statusCode)
            assertEquals(Severity.Info, response.severity)
            assertEquals(headerValue, response.content)
        }
    }

}