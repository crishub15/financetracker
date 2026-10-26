package com.puregold.financetracker.ui.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val PH_LOCALE: Locale = Locale.Builder().setLanguage("en").setRegion("PH").build()

/** Formats centavos as Philippine peso, e.g. 245075 -> "₱2,450.75". */
fun Long.toPeso(): String = NumberFormat.getCurrencyInstance(PH_LOCALE).format(this / 100.0)

/** e.g. "Oct 24" */
fun Long.toShortDate(): String = SimpleDateFormat("MMM d", PH_LOCALE).format(Date(this))
