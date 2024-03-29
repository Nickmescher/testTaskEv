package com.example.service

import com.example.model.Graph

object GraphValidator {
    fun validate(graph: Graph): Boolean {
        // Проверка на валидность графа
        if (graph.nodes.isEmpty() || graph.edges.isEmpty()) {
            return false
        }

        // Проверка наличия свойств у каждого узла
        for (node in graph.nodes) {
            if (node.property.isEmpty()) {
                return false
            }
        }

        // Проверка типов данных для узлов и ребер
        for (node in graph.nodes) {
            if (node.name.isBlank() || node.property.any { it.name.isBlank() || it.value.isBlank() }) {
                return false
            }
        }
        for (edge in graph.edges) {
            if (edge.name.isBlank() || edge.source.isBlank() || edge.target.isBlank()) {
                return false
            }
        }

        return true
    }
}