package com.example.currentrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.example.currentrack.domain.entities.CurrencyRateData
import com.example.currentrack.ui.home.CurrencyScreen
import com.example.currentrack.ui.theme.CurrenTrackTheme
import com.example.currentrack.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

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
                if (currencyRates.value != null && lastUpdatedTime.value != null) {
                    CurrencyScreen(currencyRates.value!!, lastUpdatedTime.value!!)
                }

            }
        }
    }
}

@Composable
fun CurrencyRateList(currencyRateData: CurrencyRateData) {
    LazyColumn {
        items(currencyRateData.rates.size) { currencyRate ->
            Text(text = "${currencyRateData.rates[currencyRate].symbol}: ${currencyRateData.rates[currencyRate].price}")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrenTrackTheme {
        Greeting("Android")
    }
}