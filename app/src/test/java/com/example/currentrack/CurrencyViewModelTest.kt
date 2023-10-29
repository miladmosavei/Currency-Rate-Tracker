import com.example.currentrack.domain.usecases.CurrencyRateUseCase
import com.example.currentrack.viewmodel.CurrencyViewModel
import com.example.currentrack.viewmodel.error.ShowErrorDelegate
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CurrencyViewModelTest {

    lateinit var currencyViewModel: CurrencyViewModel

    @Before
    fun setup() {
        val currencyRateUseCase = mockk<CurrencyRateUseCase>()
        val showErrorDelegate = mockk<ShowErrorDelegate>()
        currencyViewModel = spyk(CurrencyViewModel(currencyRateUseCase, showErrorDelegate))
    }

    @After
    fun cleanup() {
        unmockkAll()
    }

    @Test
    fun `test getCurrentTimeFormatted returns formatted time string`() {
        val formattedTime = currencyViewModel.getCurrentTimeFormatted()

        assertThat(formattedTime).isNotNull()
        Assert.assertTrue(formattedTime.matches("\\d{2}/\\d{2}/\\d{4} - \\d{2}:\\d{2}".toRegex()))
    }
}
