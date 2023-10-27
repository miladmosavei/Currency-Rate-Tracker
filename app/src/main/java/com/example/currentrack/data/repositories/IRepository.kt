package com.example.currentrack.data.repositories

import com.example.currentrack.domain.entities.CurrencyRateData

interface IRepository {
    abstract class CurrencyRateRepository {
        abstract suspend fun fetchCurrencyRates(): Result<CurrencyRateData>
    }
}