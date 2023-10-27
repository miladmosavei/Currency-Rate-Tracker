package com.example.currentrack.di

import com.example.currentrack.model.CurrencyRateModel
import com.example.currentrack.viewmodel.timer.CurrencyRateTimer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyRateModel(): CurrencyRateModel {
        return CurrencyRateModel()
    }

    @Singleton
    @Provides
    fun provideCurrencyRateTimer(): CurrencyRateTimer {
        return CurrencyRateTimer(120000)
    }
}
