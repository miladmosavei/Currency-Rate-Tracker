package com.example.currentrack.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.currentrack.R
import com.example.currentrack.domain.entities.CurrencyRate
import com.example.currentrack.domain.entities.CurrencyRateData
import com.example.currentrack.ui.theme.CurrenTrackTheme
import com.example.currentrack.ui.theme.ICONE_SCALE
import com.example.currentrack.ui.theme.Shapes

@Preview
@Composable
fun previewCurrencyScreen() {
    CurrenTrackTheme {
        val currencyExampleList = arrayListOf<CurrencyRate>()
        for (i in 0 until 5) {
            currencyExampleList.add(
                CurrencyRate(
                    stringResource(R.string.sample_currency), stringResource(
                        R.string.sample_price
                    )
                )
            )
        }
        val currencyRateData = CurrencyRateData(currencyExampleList)
        CurrencyScreen(currencyRateData, stringResource(R.string.sample_date))
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
            text = stringResource(R.string.rates),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.start_padding),
                    top = dimensionResource(id = R.dimen.top_padding)
                ),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h1,
        )
        RateList(currencyRateData, lastUpdate)
        LastUpdateDate(lastUpdate)
    }
}

@Preview
@Composable
fun previewRateList() {
    val currencyExampleList = arrayListOf<CurrencyRate>()
    for (i in 0 until 10) {
        currencyExampleList.add(CurrencyRate(stringResource(R.string.sample_currency), stringResource(
            id = R.string.sample_price
        )))
    }
    val currencyRateData = CurrencyRateData(currencyExampleList)
    RateList(currencyRateData = currencyRateData, stringResource(id = R.string.sample_date))
}

@Composable
fun RateList(currencyRateData: CurrencyRateData, lastUpdate: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
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
        RateItem(
            currencyRate = CurrencyRate(
                stringResource(R.string.sample_currency), stringResource(
                    id = R.string.sample_price
                )
            )
        )
    }
}

@Composable
fun RateItem(currencyRate: CurrencyRate) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.items_margin))
        ,
        elevation = dimensionResource(id = R.dimen.elevation), shape = Shapes.medium,
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
            if (currencyRate.priceIncreased == null) {
                NormalCurrencyPrice(currencyRate = currencyRate)
            } else {
                CurrencyPrice(currencyRate)
            }
        }
    }
}

@Composable
private fun CurrencyPrice(currencyRate: CurrencyRate) {
    Text(
        text = currencyRate.price,
        color = if (currencyRate.priceIncreased == true) colorResource(id = R.color.increased_price) else colorResource(
            id = R.color.decreased_price
        ),
        style = MaterialTheme.typography.caption
    )
    Icon(
        painter = painterResource(id = if (currencyRate.priceIncreased == true) R.drawable.arrow_up else R.drawable.arrow_down),
        contentDescription = if (currencyRate.priceIncreased == true) stringResource(R.string.arrow_up_description) else stringResource(
            R.string.arrow_down_description
        ),
        tint = if (currencyRate.priceIncreased == true) colorResource(id = R.color.increased_price) else colorResource(
            id = R.color.decreased_price
        ),
        modifier = Modifier
            .scale(ICONE_SCALE)
            .padding(start = dimensionResource(id = R.dimen.padding_tiny))
    )
}

@Composable
private fun NormalCurrencyPrice(currencyRate: CurrencyRate) {
    Text(
        text = currencyRate.price,
        style = MaterialTheme.typography.caption,
    )
}

@Preview
@Composable
fun previewLastUpdateDate() {
    CurrenTrackTheme {
        LastUpdateDate(lastUpdate = stringResource(R.string.sample_updated_list))
    }
}

@Composable
fun LastUpdateDate(lastUpdate: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.last_update_top_padding),
                bottom = dimensionResource(
                    id = R.dimen.last_update_buttom_padding
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.last_update),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary
            )
            Text(
                text = lastUpdate, style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}


@Composable
fun getFlagResource(country: String): Int {
    return when (country) {
        stringResource(R.string.eur_usd) -> R.drawable.eur_usd
        stringResource(R.string.gbp_jpy) -> R.drawable.gbp_jpy
        stringResource(R.string.aud_cad) -> R.drawable.aud_cad
        stringResource(R.string.jpy_aed) -> R.drawable.jpy_aed
        stringResource(R.string.jpy_sek) -> R.drawable.jpy_sek
        stringResource(R.string.usd_gbp) -> R.drawable.usd_gbp
        else -> R.drawable.usd_cad
    }
}
