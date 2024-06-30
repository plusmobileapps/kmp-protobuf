@file:OptIn(ExperimentalSerializationApi::class)

package com.plusmobileapps.protobuf

import SERVER_PORT
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.protobuf.protobuf
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondBytes
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.ExperimentalSerializationApi

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        protobuf()
    }
    routing {
        get("/") {
            val dog = Dog(name = "Fido", breed = "Golden Retriever", birthday = "2021-01-01")
            call.respondBytes(
                bytes = dog.encode(),
                contentType = ContentType.Application.ProtoBuf,
                status = HttpStatusCode.OK
            )
        }
    }
}