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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        for (i in 0 until 5) {
            currencyExampleList.add(CurrencyRate("EURUSD", "100.0"))
        }
        val currencyRateData = CurrencyRateData(currencyExampleList)
        CurrencyScreen(currencyRateData, "30/03/2023 - 16:00")
    }
}

@Composable
fun CurrencyScreen(currencyRateData: CurrencyRateData, lastUpdate: String) {
    CurrenTrackTheme {
        Main(currencyRateData, lastUpdate)
    }
}


@Composable
private fun Main(currencyRateData: CurrencyRateData, lastUpdate: String) {
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
        RateList(currencyRateData = currencyRateData, lastUpdate)

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
    RateList(currencyRateData = currencyRateData, "30/03/2023 - 16:00")
}

@Composable
fun RateList(currencyRateData: CurrencyRateData, lastUpdate: String) {
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
                RateItem(currencyRateData.rates[index])
            }
            item {
                LastUpdateDate(lastUpdate)
            }
        }
    }
}

@Preview
@Composable
fun preViewRateItem() {
    CurrenTrackTheme {
        RateItem(currencyRate = CurrencyRate("EURUSD", "100.0"))
    }
}

@Composable
fun RateItem(currencyRate: CurrencyRate) {
    Card(
        modifier = Modifier
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
                text = currencyRate.price,
                color = if (currencyRate.priceIncreased) colorResource(id = R.color.increased_price) else colorResource(
                    id = R.color.decreased_price
                ),
                style = MaterialTheme.typography.caption
            )
            Icon(
                painter = painterResource(id = if (currencyRate.priceIncreased) R.drawable.arrow_up else R.drawable.arrow_down),
                contentDescription = if (currencyRate.priceIncreased) "Up arrow" else "Down arrow",
                tint = if (currencyRate.priceIncreased) colorResource(id = R.color.increased_price) else colorResource(
                    id = R.color.decreased_price
                ),
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
    }
}

@Preview
@Composable
fun previewLastUpdateDate() {
    CurrenTrackTheme {
        LastUpdateDate(lastUpdate = "Last updated: 30/03/2023 - 16:00")
    }
}

@Composable
fun LastUpdateDate(lastUpdate: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 70.dp, bottom = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text(text = stringResource(id = R.string.last_update), style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary)
            Text(text = lastUpdate, style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary)
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
