package com.example.currentrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentrack.domain.entities.CurrencyRateData
import com.example.currentrack.domain.usecases.CurrencyRateUseCase
import com.example.currentrack.viewmodel.timer.CurrencyRateTimer
import com.example.currentrack.viewmodel.timer.TimerCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val timer: CurrencyRateTimer,
    private val currencyRateUseCase: CurrencyRateUseCase
): ViewModel() {
    private val _currencyRateLiveData = MutableLiveData<CurrencyRateData>()
    val currencyRateLiveData: LiveData<CurrencyRateData> = _currencyRateLiveData
    init {
        timer.setTimerCallback(object : TimerCallback {
            override fun onTimerTick() {
                viewModelScope.launch {
                    fetchData()
                }
            }
        })
        timer.start()
    }

    suspend fun fetchData() {
        val currencyRate = currencyRateUseCase.getCurrencyRate()
        currencyRate.collect{
            it.onSuccess {
                _currencyRateLiveData.value = it
            }
            it.onFailure {
                it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.stop()
    }
}