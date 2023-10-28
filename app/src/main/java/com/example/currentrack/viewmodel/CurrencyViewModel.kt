package com.example.currentrack.viewmodel

import androidx.lifecycle.*
import com.example.currentrack.domain.entities.CurrencyRateData
import com.example.currentrack.domain.usecases.CurrencyRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRateUseCase: CurrencyRateUseCase
) : ViewModel(), DefaultLifecycleObserver {
    private val _currencyRateLiveData = MutableLiveData<CurrencyRateData>()
    val currencyRateLiveData: LiveData<CurrencyRateData> = _currencyRateLiveData
    private val refreshIntervalMillis = 2 * 60 * 1000L

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

    private fun startPeriodicTask() {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                fetchData()
                delay(refreshIntervalMillis)
            }
        }
    }

    private suspend fun fetchData() {
        val currencyRate = currencyRateUseCase.getCurrencyRate()
        currencyRate.collect {
            it.onSuccess {
                _currencyRateLiveData.postValue(it)
            }
            it.onFailure {
                // Handle the error as needed
            }
        }
    }
}
