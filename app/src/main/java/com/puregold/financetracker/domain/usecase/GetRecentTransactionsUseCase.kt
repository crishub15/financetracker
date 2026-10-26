package com.puregold.financetracker.domain.usecase

import com.puregold.financetracker.domain.model.Transaction
import com.puregold.financetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecentTransactionsUseCase(private val transactionRepository: TransactionRepository) {
    operator fun invoke(limit: Int = 5): Flow<List<Transaction>> =
        transactionRepository.observeTransactions().map { transactions ->
            transactions.sortedByDescending { it.timestamp }.take(limit)
        }
}
