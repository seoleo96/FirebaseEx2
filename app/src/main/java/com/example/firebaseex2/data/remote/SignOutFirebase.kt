package com.example.firebaseex2.data.remote

import com.example.firebaseex2.MainActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit


interface SignOutFirebase {

    suspend fun authUser(phoneNumber: String)
    fun onCodeSent(): Flow<String>

    class Base(private val context: MainActivity) : SignOutFirebase {
        private var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
        private val AUTH: FirebaseAuth by lazy {
            FirebaseAuth.getInstance()
        }
        private val _onCodeSentFlow: MutableStateFlow<String> = MutableStateFlow("")

        override suspend fun authUser(number: String) {
            val options = PhoneAuthOptions.newBuilder(AUTH)
                .setPhoneNumber(number)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(context)             // Activity (for callback binding)
                .setCallbacks(mCallback)          // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

        override fun onCodeSent(): Flow<String> {
            return _onCodeSentFlow
        }

        init {
            mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
                override fun onVerificationFailed(p0: FirebaseException) {}
                override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                    if (id.isEmpty()) {
                        _onCodeSentFlow.value = ""
                    } else {
                        _onCodeSentFlow.value = id
                    }
                }
            }
        }
    }
}