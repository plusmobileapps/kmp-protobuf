plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "com.plusmobileapps.protobuf"
version = "1.0.0"
application {
    mainClass.set("com.plusmobileapps.protobuf.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(projects.protobuf)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}