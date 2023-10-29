package com.example.currentrack.data.mappers.base

import com.example.currentrack.data.enum.Error
import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegate
import com.example.currentrack.data.validators.IJsonValidator
import com.example.currentrack.data.base.CustomException
import com.example.currentrack.data.conts.JSON_IS_NOT_VALID
import com.example.currentrack.data.conts.THERE_IS_NO_DATA
import retrofit2.Response

interface ResponseMapper<DTO, T> {
    val failedMapperDelegate: FailedMapperDelegate

    fun map(input: Response<DTO>?): Result<T> {
        return input?.body()?.let { createModelFromDTO(input) }
            ?.let { Result.success(it) }
            ?: if (input?.code() == 400) {
                failedMapperDelegate.mapFailure(CustomException(Error.VALIDATION_ERROR, JSON_IS_NOT_VALID))
            }else
                failedMapperDelegate.mapFailure(CustomException(Error.NullObject, THERE_IS_NO_DATA))
    }

    fun createModelFromDTO(input: Response<DTO>): T

}