package com.example.currentrack.di

import com.example.currentrack.data.mappers.CurrencyRateMapper
import com.example.currentrack.data.repositories.CurrencyRateRepository
import com.example.currentrack.data.repositories.safecall.SafeCallDelegateImpl
import com.example.currentrack.data.services.CurrencyRateService
import com.example.currentrack.domain.usecases.CurrencyRateUseCase
import com.example.currentrack.viewmodel.timer.CurrencyRateTimer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyRateViewModelModule {

    @Singleton
    @Provides
    fun provideCurrencyRateTimer(): CurrencyRateTimer {
        return CurrencyRateTimer(120000)
    }

    @Provides
    fun provideCurrencyRateUseCase(repository: CurrencyRateRepository):CurrencyRateUseCase {
        return CurrencyRateUseCase(repository)
    }

    @Provides
    fun provideCurrencyRateRepository(
        service: CurrencyRateService,
        mapper: CurrencyRateMapper,
        safeCallDelegateImpl: SafeCallDelegateImpl
    ): CurrencyRateRepository {
        return CurrencyRateRepository(service,mapper,safeCallDelegateImpl)
    }
}
