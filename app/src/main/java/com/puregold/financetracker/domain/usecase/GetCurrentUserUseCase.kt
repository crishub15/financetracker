package com.puregold.financetracker.domain.usecase

import com.puregold.financetracker.domain.model.User
import com.puregold.financetracker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow

class GetCurrentUserUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): StateFlow<User?> = authRepository.currentUser
}
