package com.shahid

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.jackson.*
import kotlinx.coroutines.runBlocking

class TFLBuses(val APIKey: String) {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    private suspend fun TFLGet(url: String, queryParameters: Array<Array<String>> = arrayOf()): JsonNode? {
        val res = client.get(url) {
            url {
//                    parameters.append("app_id", APIID)
                parameters.append("app_key", APIKey)
                for (query in queryParameters) {
                    parameters.append(query[0], query[1])
                }
            }
        }

        val mapper = ObjectMapper()

        val text = res.bodyAsText()
        val json = mapper.readTree(text)

        return json
    }

    suspend fun getArrivals(bus: Int): JsonNode? {
        val res = TFLGet("https://api.tfl.gov.uk/line/$bus/arrivals")
        return res
    }
}