package com.example.currentrack.data.repositories.safecall

import com.example.currentrack.data.base.CustomException
import com.example.currentrack.data.conts.NO_CONNECTIVITY
import com.example.currentrack.data.enum.Error
import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegate
import com.example.currentrack.data.repositories.connectivity.ConnectivityDelegate
import javax.inject.Inject

class SafeCallDelegateImpl @Inject constructor(
    private val failedMapperDelegate: FailedMapperDelegate,
    private val connectivityDelegateImpl: ConnectivityDelegate
) : SafeCallDelegate,
    FailedMapperDelegate by failedMapperDelegate {
    override suspend fun <T> safeCall(call: suspend () -> Result<T>): Result<T> {
        return if (connectivityDelegateImpl.isConnected()) {
            try {
                call.invoke()
            } catch (e: Exception) {
                mapFailure(e)
            }
        } else {
            mapFailure(CustomException(Error.VALIDATION_ERROR, NO_CONNECTIVITY))
        }
    }
}