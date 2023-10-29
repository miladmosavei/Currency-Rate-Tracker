import com.example.currentrack.data.interceptor.ResponseValidationInterceptor
import com.example.currentrack.data.validators.IJsonValidator
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import okhttp3.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResponseValidationInterceptorTest {

    lateinit var interceptor: ResponseValidationInterceptor
    lateinit var jsonValidator: IJsonValidator
    lateinit var chain: Interceptor.Chain
    lateinit var request: Request

    @Before
    fun setup() {
        jsonValidator = mockk()
        interceptor = ResponseValidationInterceptor(jsonValidator)
        chain = mockk()
        request = Request.Builder().url("http://example.com").build()
        every { chain.request() } returns request
    }

    @After
    fun cleanup() {
        unmockkAll()
    }

    @Test
    fun `test intercept with valid JSON response`() {
        // Arrange
        val validJson = "{\"key\":\"value\"}"
        every { jsonValidator.isValid(validJson) } returns true
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(MediaType.parse("application/json"), validJson))
            .build()
        every { chain.proceed(request) } returns response

        // Act
        val result = interceptor.intercept(chain)

        // Assert
        assertThat(result.code()).isEqualTo(200)
    }

    @Test
    fun `test intercept with invalid JSON response`() {
        // Arrange
        val invalidJson = "{\"key\": value\"}"
        every { jsonValidator.isValid(invalidJson) } returns false
        val response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(MediaType.parse("application/json"), invalidJson))
            .build()
        every { chain.proceed(request) } returns response

        // Act
        val result = interceptor.intercept(chain)

        // Assert
        assertThat(result.code()).isEqualTo(400)
        assertThat(result.message()).isEqualTo("JSON validation failed")
    }
}
