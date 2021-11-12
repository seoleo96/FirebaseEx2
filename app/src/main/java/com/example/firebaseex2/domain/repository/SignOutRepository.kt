package com.example.firebaseex2.domain.repository

import kotlinx.coroutines.flow.Flow

interface SignOutRepository {
    suspend fun authUser(number: String)
    fun onCodeSent() : Flow<String>
}