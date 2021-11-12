package com.example.firebaseex2.data

import com.example.firebaseex2.data.remote.SignOutFirebase
import com.example.firebaseex2.domain.repository.SignOutRepository
import kotlinx.coroutines.flow.Flow

class SignOutRepositoryImpl(private val signOutFirebase: SignOutFirebase) : SignOutRepository {

    override fun onCodeSent(): Flow<String> {
        return signOutFirebase.onCodeSent()
    }

    override suspend fun authUser(number: String) {
        signOutFirebase.authUser(phoneNumber = number)
    }


}