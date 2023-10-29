package com.example.currentrack.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.currentrack.domain.entities.CurrencyRateData
import com.example.currentrack.domain.usecases.CurrencyRateUseCase
import com.example.currentrack.viewmodel.error.ShowErrorDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRateUseCase: CurrencyRateUseCase,
    private val showErrorDelegate: ShowErrorDelegate
) : ViewModel(), DefaultLifecycleObserver, ShowErrorDelegate by showErrorDelegate {
    private val _currencyRateLiveData = MutableLiveData<CurrencyRateData>()
    val currencyRateLiveData: LiveData<CurrencyRateData> = _currencyRateLiveData
    private var previousCurrencyRateDate: CurrencyRateData? = null
    private val refreshIntervalMillis = 2 * 1 * 1000L
    private val _updatedDate = MutableLiveData<String>()
    val updatedDate: LiveData<String> = _updatedDate
    private val _loadingData: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingData: LiveData<Boolean> = _loadingData
    private var job: Job? = null

    override fun onCreate(owner: LifecycleOwner) {
        startPeriodicTask()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        job?.cancel()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        job?.cancel()
    }

    /**
     * The function checks if a job is cancelled and starts a periodic task if it is.
     *
     * @param owner The owner parameter is a LifecycleOwner object that represents the lifecycle of the
     * component that this onResume method belongs to. In this case MainActivity
     */
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (job?.isCancelled == true)
            startPeriodicTask()
    }

    /**
     * The function starts a periodic task that fetches data at regular intervals.
     */
    private fun startPeriodicTask() {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                _loadingData.postValue(true)
                fetchData()
                delay(refreshIntervalMillis)
            }
        }
    }

    /**
     * The function fetches currency rate data, compares and updates it, and then updates the live data
     * with the new data and the current time.
     */
    private suspend fun fetchData() {
        val currencyRate = currencyRateUseCase.getCurrencyRate()
        currencyRate.collect {
            _loadingData.postValue(false)
            it.onSuccess { currencyRateData ->
                hideErrorDialog()
                val newCurrencyRateData = compareAndUpdateRates(
                    previousCurrencyRateDate ?: currencyRateData,
                    currencyRateData
                )
                previousCurrencyRateDate = newCurrencyRateData
                _currencyRateLiveData.postValue(newCurrencyRateData)
                _updatedDate.postValue(getCurrentTimeFormatted())
            }.onFailure { throwable ->
                onFailure(throwable)
            }
        }
    }

    /**
     * The function compares the rates of two sets of currency data and updates the "priceIncreased"
     * property of each rate based on whether the new price is higher than the previous price.
     *
     * @param previousData The previousData parameter is an instance of the CurrencyRateData class,
     * which contains a list of CurrencyRate objects. Each CurrencyRate object represents a currency
     * rate and has properties such as symbol (currency symbol) and price (currency price).
     * @param newData The `newData` parameter is an instance of the `CurrencyRateData` class, which
     * contains a list of currency rates. Each currency rate has a symbol and a price.
     * @return a new instance of `CurrencyRateData` with the updated rates.
     */
    internal fun compareAndUpdateRates(
        previousData: CurrencyRateData,
        newData: CurrencyRateData
    ): CurrencyRateData {
        val previousRatesMap = previousData.rates.associateBy { it.symbol }
        val updatedRates = newData.rates.toMutableList()

        for (newRate in updatedRates) {
            val previousRate = previousRatesMap[newRate.symbol]

            if (previousRate != null) {
                val newPrice = newRate.price.toDoubleOrNull()
                val previousPrice = previousRate.price.toDoubleOrNull()

                if (newPrice != null && previousPrice != null) {
                    newRate.priceIncreased = newPrice > previousPrice
                }
            }
        }

        return CurrencyRateData(updatedRates)
    }

    /**
     * @return the current date and time formatted as a string in the format "dd/MM/yyyy - HH:mm".
     */
    internal fun getCurrentTimeFormatted(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")
        return current.format(formatter)
    }
}
