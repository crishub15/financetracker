package com.puregold.financetracker.di

import com.puregold.financetracker.data.repository.FakeAuthRepository
import com.puregold.financetracker.data.repository.FakeTransactionRepository
import com.puregold.financetracker.domain.repository.AuthRepository
import com.puregold.financetracker.domain.repository.TransactionRepository
import com.puregold.financetracker.domain.usecase.GetCurrentUserUseCase
import com.puregold.financetracker.domain.usecase.GetDashboardSummaryUseCase
import com.puregold.financetracker.domain.usecase.GetRecentTransactionsUseCase
import com.puregold.financetracker.domain.usecase.LoginUseCase
import com.puregold.financetracker.domain.usecase.LogoutUseCase
import com.puregold.financetracker.domain.usecase.ValidateLoginInputUseCase

/**
 * Manual DI container. One instance lives in [com.puregold.financetracker.FinanceTrackerApp].
 * Swap the Fake* repositories for real implementations here — nothing else changes.
 */
class AppContainer {
    // Repositories
    val authRepository: AuthRepository by lazy { FakeAuthRepository() }
    val transactionRepository: TransactionRepository by lazy { FakeTransactionRepository() }

    // Use cases
    val validateLoginInputUseCase by lazy { ValidateLoginInputUseCase() }
    val loginUseCase by lazy { LoginUseCase(authRepository) }
    val logoutUseCase by lazy { LogoutUseCase(authRepository) }
    val getCurrentUserUseCase by lazy { GetCurrentUserUseCase(authRepository) }
    val getDashboardSummaryUseCase by lazy { GetDashboardSummaryUseCase(transactionRepository) }
    val getRecentTransactionsUseCase by lazy { GetRecentTransactionsUseCase(transactionRepository) }
}
