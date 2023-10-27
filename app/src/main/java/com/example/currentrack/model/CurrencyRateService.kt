package com.example.currentrack.model

import com.example.currentrack.model.dto.CurrencyRateResponse
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyRateService {
    @GET("code-challenge/index.php")
    fun getCurrencyRates(): Call<CurrencyRateResponse>
}
