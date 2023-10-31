package com.example.currentrack

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.livedata.observeAsState
import com.example.currentrack.ui.error.Dialog
import com.example.currentrack.ui.home.CurrencyScreen
import com.example.currentrack.ui.loading.Loading
import com.example.currentrack.ui.theme.CurrenTrackTheme
import com.example.currentrack.presentation.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val currencyRateViewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(currencyRateViewModel)
        setContent {
            CurrenTrackTheme {
                val currencyRates = currencyRateViewModel.currencyRateLiveData.observeAsState()
                val lastUpdatedTime = currencyRateViewModel.updatedDate.observeAsState()
                val loadingData = currencyRateViewModel.loadingData.observeAsState()
                val errorState = currencyRateViewModel.getErrorDialogState().observeAsState()

                if (loadingData.value == true) {
                    Loading()
                }
                if (currencyRates.value != null && lastUpdatedTime.value != null) {
                    CurrencyScreen(currencyRates.value!!, lastUpdatedTime.value!!)
                }
                if (errorState.value == true) {
                    Dialog(currencyRateViewModel, currencyRateViewModel.getErrorMessage())
                }
            }
        }
    }
}