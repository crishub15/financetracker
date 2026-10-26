package com.puregold.financetracker.domain.usecase

import com.puregold.financetracker.domain.model.Transaction
import com.puregold.financetracker.domain.model.TransactionType
import com.puregold.financetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UseCaseTest {

    private val validate = ValidateLoginInputUseCase()

    @Test
    fun `valid input passes`() {
        assertTrue(validate("demo@finance.app", "demo123").isValid)
    }

    @Test
    fun `bad email and short password fail`() {
        val result = validate("demo@", "123")
        assertFalse(result.isValid)
        assertNotNull(result.emailError)
        assertNotNull(result.passwordError)
    }

    @Test
    fun `summary splits balance and current-month totals`() = runBlocking {
        val now = 1_761_480_000_000L // fixed clock
        val day = 24L * 60 * 60 * 1000
        val repo = object : TransactionRepository {
            override fun observeTransactions(): Flow<List<Transaction>> = flowOf(
                listOf(
                    Transaction("1", "Salary", "Income", 100_00, TransactionType.INCOME, now),
                    Transaction("2", "Food", "Food", 30_00, TransactionType.EXPENSE, now),
                    Transaction("3", "Old rent", "Housing", 50_00, TransactionType.EXPENSE, now - 60 * day),
                ),
            )
        }

        val summary = GetDashboardSummaryUseCase(repo) { now }().first()

        assertEquals(20_00L, summary.balance)
        assertEquals(100_00L, summary.monthIncome)
        assertEquals(30_00L, summary.monthExpense)
    }
}
