package com.example.currentrack.viewmodel.timer

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class CurrencyRateTimer @Inject constructor(private val interval: Long) {
    private var timer: Timer? = null
    private var timerCallback: TimerCallback? = null

    fun setTimerCallback(callback: TimerCallback) {
        timerCallback = callback
    }

    fun start() {
        if (timer == null) {
            timer = Timer()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    timerCallback?.onTimerTick()
                }
            }, 0, interval)
        }
    }

    fun stop() {
        timer?.cancel()
        timer = null
    }
}
