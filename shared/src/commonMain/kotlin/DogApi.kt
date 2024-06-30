import com.plusmobileapps.protobuf.Dog
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

object DogApi {
    private val httpClient = HttpClient()

    suspend fun getDog(): Result<Dog> = withContext(Dispatchers.IO) {
        try {
            val response: HttpResponse = httpClient.get("http://localhost:8080/")
            val dog: Dog = response.receiveProtoBuf(Dog.ADAPTER)
            Result.success(dog)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}