import com.github.jamowei.common.client.Port
import com.github.jamowei.person.client.PersonClient
import com.github.jamowei.person.model.Person
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ClientTest {

    val client = PersonClient("localhost", Port.HTTP)
    val person = Person("Peter", 45, "peter@schwarz.de", 1.85)

    @Test
    fun testSave() {

        suspend {
            val response = client.save(person)
            Assertions.assertEquals(200, response.statusCode.value)
        }
    }

    @Test
    fun testLoad() {
        suspend {
            val response = client.load(person.name)
            Assertions.assertEquals(200, response.statusCode.value)
            Assertions.assertEquals(person, response.body)
        }
    }

}