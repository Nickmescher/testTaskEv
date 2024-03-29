package com.example.controller

import com.example.service.GraphService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class GetDataEndpoint(private val graphService: GraphService) {
    suspend fun handle(call: ApplicationCall, uuid: String) {
        if (uuid == null) {
            call.respond(HttpStatusCode.BadRequest, "UUID parameter is missing")
            return
        }

        val graph = graphService.getGraph(uuid)
        if (graph == null) {
            call.respond(HttpStatusCode.InternalServerError, "Graph not found")
            return
        }

        call.respond(HttpStatusCode.OK, graph)
    }
}