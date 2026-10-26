package com.puregold.financetracker.domain.usecase

data class LoginValidation(
    val emailError: String? = null,
    val passwordError: String? = null,
) {
    val isValid: Boolean get() = emailError == null && passwordError == null
}

class ValidateLoginInputUseCase {
    operator fun invoke(email: String, password: String): LoginValidation {
        val trimmed = email.trim()
        val emailError = when {
            trimmed.isEmpty() -> "Email is required"
            !EMAIL_REGEX.matches(trimmed) -> "Enter a valid email address"
            else -> null
        }
        val passwordError = when {
            password.isEmpty() -> "Password is required"
            password.length < MIN_PASSWORD_LENGTH -> "Password must be at least $MIN_PASSWORD_LENGTH characters"
            else -> null
        }
        return LoginValidation(emailError, passwordError)
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
        val EMAIL_REGEX = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
    }
}
