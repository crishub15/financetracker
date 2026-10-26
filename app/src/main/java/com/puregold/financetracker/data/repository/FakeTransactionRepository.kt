package com.puregold.financetracker.data.repository

import com.puregold.financetracker.domain.model.Transaction
import com.puregold.financetracker.domain.model.TransactionType
import com.puregold.financetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Seeded in-memory transactions. Swap for Room / API later. */
class FakeTransactionRepository(
    now: Long = System.currentTimeMillis(),
) : TransactionRepository {

    private val transactions = MutableStateFlow(seed(now))

    override fun observeTransactions(): Flow<List<Transaction>> = transactions.asStateFlow()

    private fun seed(now: Long): List<Transaction> {
        fun daysAgo(days: Int) = now - days * DAY_MS
        fun pesos(value: Double) = Math.round(value * 100)
        return listOf(
            Transaction("t1", "Salary", "Income", pesos(35_000.00), TransactionType.INCOME, daysAgo(1)),
            Transaction("t2", "Groceries", "Food", pesos(2_450.75), TransactionType.EXPENSE, daysAgo(2)),
            Transaction("t3", "Electric bill", "Utilities", pesos(3_120.00), TransactionType.EXPENSE, daysAgo(3)),
            Transaction("t4", "Freelance project", "Income", pesos(8_500.00), TransactionType.INCOME, daysAgo(5)),
            Transaction("t5", "Jeep & MRT fare", "Transport", pesos(350.00), TransactionType.EXPENSE, daysAgo(6)),
            Transaction("t6", "Dinner out", "Dining", pesos(1_200.00), TransactionType.EXPENSE, daysAgo(8)),
            Transaction("t7", "Internet", "Utilities", pesos(1_699.00), TransactionType.EXPENSE, daysAgo(12)),
            Transaction("t8", "Salary", "Income", pesos(35_000.00), TransactionType.INCOME, daysAgo(32)),
            Transaction("t9", "Rent", "Housing", pesos(12_000.00), TransactionType.EXPENSE, daysAgo(35)),
        )
    }

    private companion object {
        const val DAY_MS = 24L * 60 * 60 * 1000
    }
}
