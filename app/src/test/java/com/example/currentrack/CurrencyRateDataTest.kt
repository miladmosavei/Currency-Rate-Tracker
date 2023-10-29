import com.example.currentrack.domain.entities.CurrencyRate
import com.example.currentrack.domain.entities.CurrencyRateData
import com.example.currentrack.domain.usecases.CurrencyRateUseCase
import com.example.currentrack.viewmodel.CurrencyViewModel
import com.example.currentrack.viewmodel.error.ShowErrorDelegate
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Before
import org.junit.Test

class CurrencyRateDataTest {

    lateinit var currencyViewModel: CurrencyViewModel

    @Before
    fun before() {
        val currencyRateUseCase = mockk<CurrencyRateUseCase>()
        val showErrorDelegate = mockk<ShowErrorDelegate>()
        currencyViewModel = spyk(CurrencyViewModel(currencyRateUseCase, showErrorDelegate))
    }

    @Test
    fun `test compareAndUpdateRates with price increase`() {
        // Arrange
        val previousRates = listOf(
            CurrencyRate("USD", "1.0")
        )
        val newData = listOf(
            CurrencyRate("USD", "1.1")
        )
        val previousData = CurrencyRateData(previousRates)
        val expectedData = CurrencyRateData(newData)

        // Act
        val result = currencyViewModel.compareAndUpdateRates(previousData, expectedData)

        // Assert
        assertThat(result.rates[0].priceIncreased).isTrue()
    }

    @Test
    fun `test compareAndUpdateRates with price decrease`() {
        // Arrange
        val previousRates = listOf(
            CurrencyRate("USD", "1.1")
        )
        val newData = listOf(
            CurrencyRate("USD", "1.0")
        )
        val previousData = CurrencyRateData(previousRates)
        val expectedData = CurrencyRateData(newData)

        // Act
        val result = currencyViewModel.compareAndUpdateRates(previousData, expectedData)

        // Assert
        assertThat(result.rates[0].priceIncreased).isFalse()
    }

    @Test
    fun `test compareAndUpdateRates with no price change`() {
        // Arrange
        val previousRates = listOf(
            CurrencyRate("USD", "1.0")
        )
        val newData = listOf(
            CurrencyRate("USD", "1.0")
        )
        val previousData = CurrencyRateData(previousRates)
        val expectedData = CurrencyRateData(newData)

        // Act
        val result = currencyViewModel.compareAndUpdateRates(previousData, expectedData)

        // Assert
        assertThat(result.rates[0].priceIncreased).isFalse()
    }
}
