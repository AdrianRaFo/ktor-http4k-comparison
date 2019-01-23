package com.adrianrafo.http4k_sample

import org.http4k.client.ApacheAsyncClient
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {

  val app =
    ServerFilters.CatchLensFailure.then (
      routes(
        "/" bind GET to { Response(OK).body("Hello, World!") }
      )
    )

  val jettyServer = app.asServer(Jetty(9000)).start()

  val request = Request(GET, "http://localhost:9000")

  val client = ApacheAsyncClient()

  client(request){
    println(it)
    jettyServer.stop()
  }

}