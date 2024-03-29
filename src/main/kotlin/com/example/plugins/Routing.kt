package com.example.plugins

import com.example.controller.GetDataEndpoint
import com.example.controller.SaveDataEndpoint
import com.example.model.Graphs.uuid
import com.example.service.GraphServiceImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val graphService = GraphServiceImpl()

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/saveData/{uuid}") {
            val uuid = call.parameters["uuid"]
            SaveDataEndpoint(graphService).handle(call, uuid.toString())
        }
        get("/getData/{uuid}") {
            val uuid = call.parameters["uuid"]
            GetDataEndpoint(graphService).handle(call, uuid.toString())
        }
    }
}
