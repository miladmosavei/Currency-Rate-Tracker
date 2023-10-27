package com.example.currentrack.data.mappers.failedmap

class FailedMapperDelegateImpl: FailedMapperDelegate {
    override fun <T>mapFailure(exception: Exception): Result<T> = Result.failure(exception)
}