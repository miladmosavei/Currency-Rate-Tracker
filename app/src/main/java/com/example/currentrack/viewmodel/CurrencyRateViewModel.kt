import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentrack.domain.usecases.CurrencyRateUseCase
import com.example.currentrack.viewmodel.timer.CurrencyRateTimer
import com.example.currentrack.viewmodel.timer.TimerCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class CurrencyRateViewModel
@Inject constructor(
//    private val timer: CurrencyRateTimer,
//    private val currencyRateUseCase: CurrencyRateUseCase
) : ViewModel() {

    init {
//        timer.setTimerCallback(object : TimerCallback {
//            override fun onTimerTick() {
//                viewModelScope.launch(Dispatchers.IO) {
//                    fetchData()
//                }
//            }
//        })
//        timer.start()
    }

    suspend fun fetchData() {
//        val currencyRate = currencyRateUseCase.getCurrencyRate()
//        currencyRate
    }

    override fun onCleared() {
        super.onCleared()
//        timer.stop()
    }
}
