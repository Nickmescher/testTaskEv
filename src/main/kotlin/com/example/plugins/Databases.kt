package com.example.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/graphs",
        user = "postgres",
        driver = "org.postgresql.Driver",
        password = "postgres"
    )
}
