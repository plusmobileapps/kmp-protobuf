import com.squareup.wire.Message
import com.squareup.wire.ProtoAdapter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.util.toByteArray

suspend inline fun <reified T : Message<T, *>> HttpResponse.receiveProtoBuf(
    adapter: ProtoAdapter<T>
): T {
    val byteArray = this.bodyAsChannel().toByteArray()
    return adapter.decode(byteArray)
}