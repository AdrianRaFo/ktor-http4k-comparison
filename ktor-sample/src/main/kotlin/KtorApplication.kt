package com.adrianrafo.ktor_sample

import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import kotlinx.coroutines.runBlocking
import java.net.URL
import java.util.concurrent.TimeUnit

fun main() {

  val server = embeddedServer(Jetty, 8080) {
    routing {
      get("/") {
        call.respondText("HELLO WORLD!")
      }
    }
  }.start()

  val client = HttpClient(Apache)
  runBlocking {
    val message = client.request<String> {
      method = HttpMethod.Get
      url(URL("http://127.0.0.1:8080"))
    }
    println(message)
  }

  server.stop(1000, 1000, TimeUnit.MILLISECONDS)

}