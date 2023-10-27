package com.example.currentrack.data.mappers

import com.example.currentrack.data.dto.CurrencyRateResponseDTO
import com.example.currentrack.data.mappers.base.ResponseMapper
import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegateImpl
import com.example.currentrack.domain.entities.CurrencyRate
import com.example.currentrack.domain.entities.CurrencyRateData
import retrofit2.Response
import javax.inject.Inject

class CurrencyRateMapper @Inject constructor(
    override val failedMapperDelegate: FailedMapperDelegateImpl,
) : ResponseMapper<CurrencyRateResponseDTO, CurrencyRateData> {

    override fun createModelFromDTO(input: Response<CurrencyRateResponseDTO>): CurrencyRateData {
        val dto = input.body()!!
        val rates = dto.rates.map { rateDTO ->
            CurrencyRate(rateDTO.symbol, rateDTO.price)
        }
        return CurrencyRateData(rates)
    }

}
