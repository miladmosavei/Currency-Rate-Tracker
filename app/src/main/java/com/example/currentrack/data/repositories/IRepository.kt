package com.example.currentrack.data.repositories

import com.example.currentrack.domain.entities.CurrencyRateResponse

interface IRepository {
    abstract class CurrencyRateRepository {
        abstract suspend fun fetchCurrencyRates(): Result<CurrencyRateResponse>
    }
}