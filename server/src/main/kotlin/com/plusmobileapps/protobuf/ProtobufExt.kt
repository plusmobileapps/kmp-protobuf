package com.plusmobileapps.protobuf

import com.squareup.wire.Message
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondBytes

suspend fun ApplicationCall.respondProtobuf(
    response: Message<*, *>,
    statusCode: HttpStatusCode? = null,
) {
    respondBytes(
        bytes = response.encode(),
        contentType = ContentType.Application.ProtoBuf,
        status = statusCode,
    )
}