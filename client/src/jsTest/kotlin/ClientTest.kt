import com.github.jamowei.common.client.Port
import com.github.jamowei.person.client.PersonClient
import com.github.jamowei.person.model.Person
import kotlin.test.Test
import kotlin.test.assertEquals

class ClientTest {

    private val client = PersonClient("localhost", Port.HTTP)
    private val person = Person("Hans", 45, "peter@schwarz.de", 1.85)

    @Test
    fun testSave() {
        suspend {
            val response = client.save(person)
            assertEquals(200, response.statusCode.value)
        }
    }

    @Test
    fun testLoad() {
        suspend {
            val response = client.load(person.name)
            assertEquals(200, response.statusCode.value)
            assertEquals(person, response.body)
        }
    }

}