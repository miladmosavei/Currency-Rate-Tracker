import com.example.currentrack.data.dto.CurrencyRateDTO
import com.example.currentrack.data.dto.CurrencyRateResponseDTO
import com.example.currentrack.data.mappers.CurrencyRateMapper
import com.example.currentrack.data.mappers.failedmap.FailedMapperDelegateImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.unmockkAll
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CurrencyRateMapperTest {

    lateinit var currencyRateMapper: CurrencyRateMapper

    @Before
    fun setup() {
        currencyRateMapper = CurrencyRateMapper(FailedMapperDelegateImpl())
    }

    @After
    fun cleanup() {
        unmockkAll()
    }

    @Test
    fun `test createModelFromDTO with valid response`() {
        // Arrange
        val rateDTO1 = CurrencyRateDTO("USD", 1.0)
        val rateDTO2 = CurrencyRateDTO("EUR", 0.9)
        val currencyRateResponseDTO = CurrencyRateResponseDTO(listOf(rateDTO1, rateDTO2))
        val response = Response.success(currencyRateResponseDTO)

        // Act
        val result = currencyRateMapper.createModelFromDTO(response)

        // Assert
        assertThat(result).isNotNull()
        assertThat(result.rates).hasSize(2)
        assertThat(result.rates[0].symbol).isEqualTo("USD")
        assertThat(result.rates[0].price).isEqualTo("1.0000")
        assertThat(result.rates[1].symbol).isEqualTo("EUR")
        assertThat(result.rates[1].price).isEqualTo("0.9000")
    }

    @Test
    fun `test createModelFromDTO with null body`() {
        // Arrange
        val response = Response.success<CurrencyRateResponseDTO>(null)

        // Act
        val result = currencyRateMapper.createModelFromDTO(response)

        // Assert
        assertThat(result).isNotNull()
        assertThat(result.rates).isEmpty()
    }

    @Test
    fun `test createModelFromDTO with failed response`() {
        // Arrange
        val response = Response.error<CurrencyRateResponseDTO>(400, ResponseBody.create(null, ""))

        // Act
        val result = currencyRateMapper.createModelFromDTO(response)

        // Assert
        assertThat(result).isNotNull()
        assertThat(result.rates).isEmpty()
    }
}
