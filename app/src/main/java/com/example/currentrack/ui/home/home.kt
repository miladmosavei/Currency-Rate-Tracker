package com.example.currentrack.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currentrack.R
import com.example.currentrack.domain.entities.CurrencyRate
import com.example.currentrack.domain.entities.CurrencyRateData
import com.example.currentrack.ui.theme.CurrenTrackTheme
import com.example.currentrack.ui.theme.Shapes

@Preview
@Composable
fun previewCurrencyScreen() {
    CurrenTrackTheme {
        val currencyExampleList = arrayListOf<CurrencyRate>()
        for (i in 0 until 10) {
            currencyExampleList.add(CurrencyRate("EURUSD", "100.0"))
        }
        val currencyRateData = CurrencyRateData(currencyExampleList)
        CurrencyScreen(currencyRateData)
    }
}

@Composable
fun CurrencyScreen(currencyRateData: CurrencyRateData) {
    CurrenTrackTheme {
        Main(currencyRateData)
    }
}


@Composable
private fun Main(currencyRateData: CurrencyRateData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Rates",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 76.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h1,
        )
        RateList(currencyRateData = currencyRateData)

    }
}

@Preview
@Composable
fun previewRateList() {
    val currencyExampleList = arrayListOf<CurrencyRate>()
    for (i in 0 until 10) {
        currencyExampleList.add(CurrencyRate("EURUSD", "100.0"))
    }
    val currencyRateData = CurrencyRateData(currencyExampleList)
    RateList(currencyRateData = currencyRateData)
}

@Composable
fun RateList(currencyRateData: CurrencyRateData) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
            modifier = Modifier.fillMaxSize()

        ) {
            items(currencyRateData.rates.size) { index ->
                RateItem(currencyRateData.rates[index], true)
            }
        }
    }
}

@Preview
@Composable
fun preViewRateItem() {
    CurrenTrackTheme {
        RateItem(currencyRate = CurrencyRate("EURUSD", "100.0"), true)
    }
}

@Composable
fun RateItem(currencyRate: CurrencyRate, isUp: Boolean) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
        elevation = 0.dp, shape = Shapes.medium,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_medium)
                ),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = getFlagResource(currencyRate.symbol)),
                contentDescription = "${currencyRate.symbol} flag",
                modifier = Modifier.size(dimensionResource(id = R.dimen.image_size))
            )
            Text(
                text = currencyRate.symbol,
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.padding_small))
                    .weight(1f),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = currencyRate.price.toString(),
                color = if (isUp) MaterialTheme.colors.secondary else MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption
            )
            Icon(
                imageVector = if (isUp) Icons.Filled.ThumbUp else Icons.Filled.ArrowDropDown,
                contentDescription = if (isUp) "Up arrow" else "Down arrow",
                tint = if (isUp) MaterialTheme.colors.secondary else MaterialTheme.colors.error,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
    }
}


fun getFlagResource(country: String): Int {
    return when (country) {
        "EURUSD" -> R.drawable.eur_usd
        "GBPJPY" -> R.drawable.gbp_jpy
        "AUDCAD" -> R.drawable.aud_cad
        "JPYAED" -> R.drawable.jpy_aed
        "JPYSEK" -> R.drawable.jpy_sek
        "USDGBP" -> R.drawable.usd_gbp
        else -> R.drawable.usd_cad
    }
}
