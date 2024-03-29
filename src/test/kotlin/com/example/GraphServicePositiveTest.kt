package com.example

import com.example.model.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class GraphServicePositiveTest {
    @Test
    fun testSaveAndGetGraph_SuccessfulScenario() = testApplication {
        val client = createClient {
            this@testApplication.install(ContentNegotiation) {
                json()
            }
        }

        val testUuid = Graphs.uuid("01c4d1ec-d1a1-4061-a6cd-0422a78b6547")

        val testGraph = Graph(
            listOf(Node("A", listOf(Property("prop1", "value1")))),
            listOf(Edge("edge1", "A", "B"))
        )

        val request = client.post("/saveData/$testUuid") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(Json.encodeToString(testGraph))
        }

        assertEquals(HttpStatusCode.Created, request.status)


        val response = client.get( "/getData/$testUuid")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(testGraph.edges, response.body<Graph>().edges)
        assertEquals(testGraph.nodes, response.body<Graph>().nodes)

    }
}