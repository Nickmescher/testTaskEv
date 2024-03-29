package com.example.service

import com.example.model.Graph

interface GraphService {
    fun saveGraph(uuid: String, graph: Graph): Boolean
    fun getGraph(uuid: String): Graph?
}