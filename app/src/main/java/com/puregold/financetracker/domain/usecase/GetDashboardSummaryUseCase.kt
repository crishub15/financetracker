package com.puregold.financetracker.domain.usecase

import com.puregold.financetracker.domain.model.DashboardSummary
import com.puregold.financetracker.domain.model.TransactionType
import com.puregold.financetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

class GetDashboardSummaryUseCase(
    private val transactionRepository: TransactionRepository,
    private val now: () -> Long = System::currentTimeMillis,
) {
    operator fun invoke(): Flow<DashboardSummary> =
        transactionRepository.observeTransactions().map { transactions ->
            val monthStart = startOfMonth(now())
            val thisMonth = transactions.filter { it.timestamp >= monthStart }
            DashboardSummary(
                balance = transactions.sumOf { it.signedAmount },
                monthIncome = thisMonth.filter { it.type == TransactionType.INCOME }.sumOf { it.amount },
                monthExpense = thisMonth.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount },
            )
        }

    private fun startOfMonth(millis: Long): Long = Calendar.getInstance().apply {
        timeInMillis = millis
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}
