package com.puregold.financetracker.domain.model

enum class TransactionType { INCOME, EXPENSE }

/**
 * Money is stored in centavos (Long) to avoid floating-point rounding errors.
 * [amount] is always positive; the sign comes from [type].
 */
data class Transaction(
    val id: String,
    val title: String,
    val category: String,
    val amount: Long,
    val type: TransactionType,
    val timestamp: Long,
) {
    val signedAmount: Long
        get() = if (type == TransactionType.INCOME) amount else -amount
}
