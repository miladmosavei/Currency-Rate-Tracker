package com.example.currentrack.data.services

import com.example.currentrack.data.dto.CurrencyRateResponseDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyRateService {
    @GET("code-challenge/index.php")
    suspend fun getCurrencyRates(): Response<CurrencyRateResponseDTO>
}
