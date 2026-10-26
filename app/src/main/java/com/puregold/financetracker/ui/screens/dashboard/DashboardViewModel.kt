package com.puregold.financetracker.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.puregold.financetracker.FinanceTrackerApp
import com.puregold.financetracker.domain.model.DashboardSummary
import com.puregold.financetracker.domain.model.Transaction
import com.puregold.financetracker.domain.usecase.GetCurrentUserUseCase
import com.puregold.financetracker.domain.usecase.GetDashboardSummaryUseCase
import com.puregold.financetracker.domain.usecase.GetRecentTransactionsUseCase
import com.puregold.financetracker.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DashboardUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val summary: DashboardSummary = DashboardSummary(0, 0, 0),
    val recentTransactions: List<Transaction> = emptyList(),
    val isLoggedOut: Boolean = false,
)

class DashboardViewModel(
    getCurrentUser: GetCurrentUserUseCase,
    getDashboardSummary: GetDashboardSummaryUseCase,
    getRecentTransactions: GetRecentTransactionsUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = combine(
        getCurrentUser(),
        getDashboardSummary(),
        getRecentTransactions(),
    ) { user, summary, recent ->
        DashboardUiState(
            isLoading = false,
            userName = user?.name.orEmpty(),
            summary = summary,
            recentTransactions = recent,
            isLoggedOut = user == null,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState(),
    )

    fun onLogoutClick() {
        viewModelScope.launch { logoutUseCase() }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val c = (this[APPLICATION_KEY] as FinanceTrackerApp).container
                DashboardViewModel(
                    getCurrentUser = c.getCurrentUserUseCase,
                    getDashboardSummary = c.getDashboardSummaryUseCase,
                    getRecentTransactions = c.getRecentTransactionsUseCase,
                    logoutUseCase = c.logoutUseCase,
                )
            }
        }
    }
}
