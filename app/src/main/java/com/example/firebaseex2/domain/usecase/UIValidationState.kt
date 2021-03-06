package com.example.firebaseex2.domain.usecase


sealed class UIValidationState {
    object Loading: UIValidationState()
    data class IsEmpty(val errorMessage: String): UIValidationState()
    data class PhoneNumberLess(val errorMessage: String): UIValidationState()
    data class Success<T>(val content: T): UIValidationState()
}