package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Graph(
    val nodes: List<Node>,
    val edges: List<Edge>
)

@Serializable
data class Node(
    val name: String,
    val property: List<Property>
)

@Serializable
data class Property(
    val name: String,
    val value: String
)

@Serializable
data class Edge(
    val name: String,
    val source: String,
    val target: String
)

object Graphs : Table("Graphs") {
    val uuid = (varchar("uuid", 36))
    val graph = text("graph")

    override val primaryKey = PrimaryKey(uuid)
}