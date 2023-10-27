package com.example.currentrack.data.repositories.safecall

import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegate

class SafeCallDelegateImpl(private val failedMapperDelegate: FailedMapperDelegate) : SafeCallDelegate,
    FailedMapperDelegate by failedMapperDelegate {


    override suspend fun <T> safeCall(call: suspend () -> Result<T>): Result<T> {
        return try {
            call.invoke()
        } catch (e: Exception) {
            mapFailure(e)
        }
    }


}