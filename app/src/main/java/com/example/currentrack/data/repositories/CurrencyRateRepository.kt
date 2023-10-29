package com.example.currentrack.data.repositories

import com.example.currentrack.data.mappers.CurrencyRateMapper
import com.example.currentrack.data.repositories.safecall.SafeCallDelegate
import com.example.currentrack.data.repositories.safecall.SafeCallDelegateImpl
import com.example.currentrack.data.services.CurrencyRateService
import com.example.currentrack.domain.entities.CurrencyRateData
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class CurrencyRateRepository @Inject constructor(
    private val service: CurrencyRateService,
    private val mapper: CurrencyRateMapper,
    private val safeCallDelegate: SafeCallDelegateImpl
): IRepository.CurrencyRateRepository(), SafeCallDelegate by safeCallDelegate {

   /**
    * The function fetches currency rates using a service and maps the result using a mapper.
    */
   override suspend fun fetchCurrencyRates(): Result<CurrencyRateData> = safeCall {
       val rates = service.getCurrencyRates()
       mapper.map(rates)
   }
}
