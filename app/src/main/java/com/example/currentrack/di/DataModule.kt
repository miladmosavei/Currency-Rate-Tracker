package com.example.currentrack.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.currentrack.data.interceptor.ResponseValidationInterceptor
import com.example.currentrack.data.mappers.CurrencyRateMapper
import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegateImpl
import com.example.currentrack.data.repositories.connectivity.ConnectivityDelegate
import com.example.currentrack.data.repositories.connectivity.ConnectivityDelegateImpl
import com.example.currentrack.data.services.CurrencyRateService
import com.example.currentrack.data.validators.JsonValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideJsonValidator(): JsonValidator {
        return JsonValidator()
    }
    @Provides
    @Singleton
    fun provideInterceptor(jsonValidator: JsonValidator): Interceptor {
        return ResponseValidationInterceptor(jsonValidator)
    }

    @Provides
    @Singleton
    fun provideOkHttp(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(10000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideLokomondServerRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://lokomond.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLokomondService(retrofit: Retrofit): CurrencyRateService {
        return retrofit.create(CurrencyRateService::class.java)
    }



    @Provides
    fun provideMapper(failedMapperDelegate: FailedMapperDelegateImpl): CurrencyRateMapper {
        return CurrencyRateMapper(failedMapperDelegate)
    }

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    fun provideConnectivityDelegate(connectivityManager: ConnectivityManager): ConnectivityDelegate {
        return ConnectivityDelegateImpl(connectivityManager)
    }

}
