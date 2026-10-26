package com.puregold.financetracker.domain.usecase

import com.puregold.financetracker.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.logout()
}
