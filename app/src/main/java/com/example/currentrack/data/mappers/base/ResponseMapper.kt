package com.example.currentrack.data.mappers.base

import com.example.currentrack.data.enum.Error
import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegate
import com.example.currentrack.data.validators.IJsonValidator
import com.example.currentrack.data.base.CustomException
import retrofit2.Response

interface ResponseMapper<DTO, T> {
    val failedMapperDelegate: FailedMapperDelegate

    fun map(input: Response<DTO>?): Result<T> {
        return input?.body()?.let { createModelFromDTO(input) }
            ?.let { Result.success(it) }
            ?: failedMapperDelegate.mapFailure(CustomException(Error.NullObject))
    }

    fun createModelFromDTO(input: Response<DTO>): T

}