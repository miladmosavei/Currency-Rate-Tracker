package com.example.currentrack.model

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CurrencyRateModel {
    // Replace this URL with your actual data source
    private val dataUrl = "https://example.com/api/rates"

    fun fetchDataFromServer(): String? {
        var response: String? = null
        try {
            val url = URL(dataUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            response = stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle errors here
        }
        return response
    }
}
