package com.example.firebaseex2.domain.usecase

import com.example.firebaseex2.domain.repository.SignOutRepository
import kotlinx.coroutines.flow.Flow

class SignOutWthPhoneNumber(private val signOutRepository: SignOutRepository) {

    suspend fun authUser(number: String) {
        signOutRepository.authUser(number = number)
    }

    fun onCodeSent(): Flow<String> {
        return signOutRepository.onCodeSent()
    }
}