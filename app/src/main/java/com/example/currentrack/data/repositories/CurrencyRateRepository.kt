package com.example.currentrack.data.repositories

import com.example.currentrack.domain.entities.CurrencyRate
import com.example.currentrack.data.mappers.CurrencyRateMapper
import com.example.currentrack.data.repositories.safecall.SafeCallDelegate
import com.example.currentrack.data.repositories.safecall.SafeCallDelegateImpl
import com.example.currentrack.data.services.CurrencyRateService
import com.example.currentrack.data.validators.JsonValidator
import com.example.currentrack.domain.entities.CurrencyRateResponse
import javax.inject.Inject

class CurrencyRateRepository @Inject constructor(
    private val service: CurrencyRateService,
    private val mapper: CurrencyRateMapper,
    private val safeCallDelegate: SafeCallDelegateImpl
): IRepository.CurrencyRateRepository(), SafeCallDelegate by safeCallDelegate {

   override suspend fun fetchCurrencyRates(): Result<CurrencyRateResponse> = safeCall {
       val rates = service.getCurrencyRates()
       mapper.map(rates)
   }
}
