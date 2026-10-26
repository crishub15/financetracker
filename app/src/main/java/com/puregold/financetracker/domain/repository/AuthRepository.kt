package com.puregold.financetracker.domain.repository

import com.puregold.financetracker.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: StateFlow<User?>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun logout()
}
