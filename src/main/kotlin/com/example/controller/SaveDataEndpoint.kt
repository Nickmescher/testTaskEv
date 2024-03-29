package com.example.controller

import com.example.model.Graph
import com.example.service.GraphService
import com.example.service.GraphValidator
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json

class SaveDataEndpoint(private val graphService: GraphService) {
    suspend fun handle(call: ApplicationCall, uuid: String) {
        val graphJson = call.receive<String>()
        val graph = Json.decodeFromString<Graph>(graphJson)

        if (uuid == null || graph == null) {
            call.respond(HttpStatusCode.BadRequest, "UUID or graph data is missing")
            return
        }

        if (!GraphValidator.validate(graph!!)) {
            call.respond(HttpStatusCode.BadRequest, "Invalid graph data")
            return
        }

        if (!graphService.saveGraph(uuid!!, graph!!)) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to save graph data")
            return
        }

        call.respond(HttpStatusCode.Created)
    }
}
