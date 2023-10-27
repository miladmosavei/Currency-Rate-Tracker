package com.example.currentrack.model

import org.json.JSONObject

class JsonValidator {
    fun validate(jsonString: String): Boolean {
        try {
            val jsonData = JSONObject(jsonString)

            if (!jsonData.has("rates")) {
                return false
            }

            val ratesArray = jsonData.getJSONArray("rates")
            for (i in 0 until ratesArray.length()) {
                val rateObject = ratesArray.getJSONObject(i)
                if (!rateObject.has("symbol") || !rateObject.has("price")) {
                    return false
                }
                val price = rateObject.optDouble("price")
                if (price.isNaN()) {
                    return false
                }
            }
        } catch (e: Exception) {
            return false
        }

        return true
    }
}
