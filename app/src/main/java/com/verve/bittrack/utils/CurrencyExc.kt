package com.verve.bittrack.utils

import java.text.NumberFormat
import java.util.Locale

fun Double?.doubleToCurrency(
    language: String,
    country: String,
): String? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.format(this).toString()
    } catch (e: Exception) {
        null
    }
}

fun Double?.toIndonesianFormat() = this.doubleToCurrency("in", "ID")

fun Double?.toDollarFormat() = this.doubleToCurrency("en", "US")
