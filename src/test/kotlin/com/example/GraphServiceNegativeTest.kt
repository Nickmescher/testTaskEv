package com.example

import com.example.model.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class GraphServiceNegativeTest {
    @Test
    fun testSaveGraph_FailureScenario() = testApplication {
        val client = createClient {
            this@testApplication.install(ContentNegotiation) {
                json()
            }
        }

        val testUuid = Graphs.uuid("01c4d1ec-d1a1-4061-a6cd-0422a78b6547")

        val request = client.post("/saveData/$testUuid") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody("invalid_json_data")
        }

        assertEquals(HttpStatusCode.InternalServerError, request.status)
    }
}