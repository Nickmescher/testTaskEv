package com.example.service

import com.example.model.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class GraphServiceImpl : GraphService {
    override fun saveGraph(uuid: String, graph: Graph): Boolean {
        return transaction {
            try {
                // Проверка наличия графа в БД
                if (Graphs.select { Graphs.uuid eq uuid }.count() > 0) {
                    return@transaction false
                }

                val graphJson = Json.encodeToString(graph)

                // Сохранение графа в БД
                val insertResult = Graphs.insert {
                    it[Graphs.uuid] = uuid
                    it[Graphs.graph] = graphJson
                }
                insertResult.insertedCount > 1
            } catch (e: Exception) {
                false
            }
        }
    }

    override fun getGraph(uuid: String): Graph? {
        return transaction {
            val graphJson = Graphs.select { Graphs.uuid eq uuid}.singleOrNull()?.get(Graphs.graph)

            graphJson?.let {
                try {
                    val graph = Json.decodeFromString<Graph>(it)
                    return@transaction graph
                } catch (e: SerializationException) {
                    throw SerializationException("Failed to deserialize object: ${e.message}")
                }
            }
        }
    }
}
