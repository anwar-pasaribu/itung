package com.unware.itung.utilities

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun main() {

    val number = 9990.01
    val formattedNumber = formatPrice(number)
    println("Hello bitch!!! Your price: $formattedNumber")
}

fun formatPrice(number: Number) : String {

    val nf = NumberFormat.getInstance(Locale.ITALIAN)
    nf.minimumFractionDigits = 0
    nf.maximumFractionDigits = 2
    (nf as DecimalFormat).isDecimalSeparatorAlwaysShown = false

    return nf.format(number)
}