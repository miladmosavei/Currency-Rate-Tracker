package com.example.currentrack.data.interceptor

import com.example.currentrack.data.validators.IJsonValidator
import com.example.currentrack.data.base.CustomException
import com.example.currentrack.data.enum.Error
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ResponseValidationInterceptor @Inject constructor(private val validator: IJsonValidator) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            throw CustomException(Error.UNSUCCESSFUL_FETCH)
        }
        val responseBody = response.body()
        if (responseBody != null) {
            try {
                val json = responseBody.string()
                if (!validator.isValid(json)) {
                    throw CustomException(Error.VALIDATION_ERROR)
                }
            } catch (e: Exception) {
                throw CustomException(Error.FAILED_PARS)
            }
        } else {
            throw CustomException(Error.VALIDATION_ERROR)
        }
        return response
    }
}
