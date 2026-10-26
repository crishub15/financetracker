package com.puregold.financetracker.data.repository

import com.puregold.financetracker.domain.model.User
import com.puregold.financetracker.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory auth for development. Replace with an API-backed implementation
 * (and persist the session token) when the backend is ready.
 */
class FakeAuthRepository : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String): Result<User> {
        delay(800) // simulate network latency
        if (!email.equals(DEMO_EMAIL, ignoreCase = true) || password != DEMO_PASSWORD) {
            return Result.failure(IllegalArgumentException("Invalid email or password"))
        }
        val user = User(id = "u-001", name = "Demo User", email = DEMO_EMAIL)
        _currentUser.value = user
        return Result.success(user)
    }

    override suspend fun logout() {
        _currentUser.value = null
    }

    companion object {
        const val DEMO_EMAIL = "demo@finance.app"
        const val DEMO_PASSWORD = "demo123"
    }
}
