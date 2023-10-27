package com.example.currentrack.model

import com.example.currentrack.domain.entities.CurrencyRate
import com.example.currentrack.model.dto.CurrencyRateResponse

class CurrencyRateMapper {

    fun mapResponseToEntity(response: CurrencyRateResponse): List<CurrencyRate> {
        val currencyRates = mutableListOf<CurrencyRate>()

        for (dto in response.rates) {
            val entity = CurrencyRate(dto.symbol, dto.price)
            currencyRates.add(entity)
        }

        return currencyRates
    }
}
