package com.example.currentrack.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.currentrack.domain.entities.CurrencyRateData
import com.example.currentrack.domain.usecases.CurrencyRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRateUseCase: CurrencyRateUseCase
) : ViewModel(), DefaultLifecycleObserver {
    private val _currencyRateLiveData = MutableLiveData<CurrencyRateData>()
    val currencyRateLiveData: LiveData<CurrencyRateData> = _currencyRateLiveData
    private var previousCurrencyRateDate: CurrencyRateData? = null
    private val refreshIntervalMillis = 2 * 1 * 1000L
    private val _updatedDate = MutableLiveData<String>()
    val updatedDate:LiveData<String> = _updatedDate
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

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (job?.isCancelled == true)
            startPeriodicTask()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startPeriodicTask() {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                _loadingData.postValue(true)
                fetchData()
                delay(refreshIntervalMillis)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun fetchData() {
        val currencyRate = currencyRateUseCase.getCurrencyRate()
        currencyRate.collect {
            _loadingData.postValue(false)
            it.onSuccess { currencyRateData ->
                val newCurrencyRateData = compareAndUpdateRates(previousCurrencyRateDate ?: currencyRateData, currencyRateData)
                previousCurrencyRateDate = newCurrencyRateData
                _currencyRateLiveData.postValue(newCurrencyRateData)
                _updatedDate.postValue(getCurrentTimeFormatted())
            }.onFailure {
                // Handle the error as needed
                it
            }
        }
    }

    private fun compareAndUpdateRates(previousData: CurrencyRateData, newData: CurrencyRateData): CurrencyRateData {
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTimeFormatted(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")
        return current.format(formatter)
    }
}
