package com.example.currentrack.domain.entities

data class CurrencyRate(val symbol: String, val price: String, var priceIncreased: Boolean? = null)
