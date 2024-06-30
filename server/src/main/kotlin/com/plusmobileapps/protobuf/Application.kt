package com.plusmobileapps.protobuf

import SERVER_PORT
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            val dog = Dog(name = "Fido", breed = "Golden Retriever", birthday = "2021-01-01")
            call.respondProtobuf(dog, HttpStatusCode.OK)
        }
    }
}