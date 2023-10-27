package com.example.currentrack.data.interceptor

import com.example.currentrack.data.validators.IJsonValidator
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import javax.inject.Inject

class ResponseValidationInterceptor @Inject constructor(private val validator: IJsonValidator) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val json = response.peekBody(Long.MAX_VALUE).string()
        if (!validator.isValid(json)) {
            return Response.Builder()
                .request(chain.request())
                .protocol(response.protocol())
                .code(400)
                .message("JSON validation failed")
                .body(createResponseBody("JSON validation failed"))
                .build()
        }
        return response
    }

    private fun createResponseBody(message: String): ResponseBody {
        val mediaType = MediaType.parse("application/json")
        val content = Buffer().writeUtf8(message)
        return ResponseBody.create(mediaType, content.size(), content)
    }
}
