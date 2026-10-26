package com.puregold.financetracker.domain.usecase

import com.puregold.financetracker.domain.model.User
import com.puregold.financetracker.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> =
        authRepository.login(email.trim(), password)
}
