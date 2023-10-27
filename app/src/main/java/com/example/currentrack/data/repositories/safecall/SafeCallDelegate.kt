package com.example.currentrack.data.repositories.safecall

interface SafeCallDelegate {

    suspend fun <T>safeCall(call: suspend () -> Result<T>): Result<T>
}