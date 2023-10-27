package com.example.currentrack.domain.usecases

import com.example.currentrack.domain.entities.CurrencyRateData
import kotlinx.coroutines.flow.Flow

interface ICurrencyRateUseCase {
    fun getCurrencyRate():Flow<Result<CurrencyRateData>>
}