@file:OptIn(ExperimentalSerializationApi::class)

import com.plusmobileapps.protobuf.Dog
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.utils.DEFAULT_HTTP_BUFFER_SIZE
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.protobuf.protobuf
import io.ktor.util.Identity.decode
import io.ktor.util.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

object DogApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            protobuf()
        }
    }

    suspend fun getDog(): Result<Dog> = withContext(Dispatchers.IO) {
        try {
            val response = httpClient.get("http://localhost:8080/") {
                headers {
                    accept(ContentType.Application.ProtoBuf)
                }
            }
            val byteArray = response.bodyAsChannel().toByteArray()
            val dog = Dog.Companion.ADAPTER.decode(byteArray)
            Result.success(dog)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}