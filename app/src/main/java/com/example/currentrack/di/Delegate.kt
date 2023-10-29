package com.example.currentrack.di

import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegate
import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegateImpl
import com.example.currentrack.data.repositories.connectivity.ConnectivityDelegate
import com.example.currentrack.data.repositories.safecall.SafeCallDelegateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object Delegate {
    @Provides
    fun provideFailedMapperDelegateImpl(): FailedMapperDelegateImpl {
        return FailedMapperDelegateImpl()
    }

    @Provides
    fun provideSafeCallDelegate(failedMapperDelegate: FailedMapperDelegateImpl,
                                connectivityDelegateImpl: ConnectivityDelegate): SafeCallDelegateImpl {
        return SafeCallDelegateImpl(failedMapperDelegate, connectivityDelegateImpl)
    }
}