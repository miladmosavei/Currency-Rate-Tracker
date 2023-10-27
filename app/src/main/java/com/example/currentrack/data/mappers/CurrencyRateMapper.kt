package com.example.currentrack.data.mappers

import com.example.currentrack.data.dto.CurrencyRateResponseDTO
import com.example.currentrack.data.mappers.base.ResponseMapper
import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegateImpl
import com.example.currentrack.data.validators.IJsonValidator
import com.example.currentrack.data.validators.JsonValidator
import com.example.currentrack.domain.entities.CurrencyRate
import com.example.currentrack.domain.entities.CurrencyRateResponse
import retrofit2.Response
import javax.inject.Inject

class CurrencyRateMapper @Inject constructor(
    override val failedMapperDelegate: FailedMapperDelegateImpl,
) : ResponseMapper<CurrencyRateResponseDTO, CurrencyRateResponse> {

    override fun createModelFromDTO(input: Response<CurrencyRateResponseDTO>): CurrencyRateResponse {
        val dto = input.body()!!
        val rates = dto.rates.map { rateDTO ->
            CurrencyRate(rateDTO.symbol, rateDTO.price)
        }
        return CurrencyRateResponse(rates)
    }

}
