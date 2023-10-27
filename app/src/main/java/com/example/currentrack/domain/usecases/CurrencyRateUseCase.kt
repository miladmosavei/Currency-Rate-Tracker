package com.example.currentrack.domain.usecases

import com.example.currentrack.data.repositories.CurrencyRateRepository
import com.example.currentrack.data.repositories.IRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
@Module
@InstallIn(ViewModelComponent::class)
class CurrencyRateUseCase @Inject constructor(private val repository: CurrencyRateRepository) :
    ICurrencyRateUseCase {
    override fun getCurrencyRate() = flow {
        val fetchCurrencyRates = repository.fetchCurrencyRates()
        emit(fetchCurrencyRates)
    }
}