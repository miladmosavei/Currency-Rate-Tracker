import androidx.lifecycle.ViewModel
import com.example.currentrack.model.CurrencyRateModel
import com.example.currentrack.viewmodel.timer.CurrencyRateTimer
import com.example.currentrack.viewmodel.timer.TimerCallback
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CurrencyRateViewModel
@Inject constructor(
    private val timer: CurrencyRateTimer,
    private val model: CurrencyRateModel
) : ViewModel() {

    init {
        // Initialize the timer with your desired interval
        timer.setTimerCallback(object : TimerCallback {
            override fun onTimerTick() {
                fetchData()
            }
        })
        timer.start()
    }

    // Fetch data from the Model layer
    fun fetchData() {
        // Implement data fetching and processing logic here using the Model
        model.fetchDataFromServer()
    }

    override fun onCleared() {
        super.onCleared()
        // Stop the timer when the ViewModel is no longer in use
        timer.stop()
    }
}
