package com.puregold.financetracker.domain.model

/** All amounts in centavos. */
data class DashboardSummary(
    val balance: Long,
    val monthIncome: Long,
    val monthExpense: Long,
)
