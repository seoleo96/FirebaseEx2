package com.example.firebaseex2.domain.usecase

import com.example.firebaseex2.toast
import com.example.firebaseex2.ui.signout.RegisterViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ValidatePhone {

    fun validate(phoneNumber : String) : Flow<UIValidationState>

    class Base : ValidatePhone{
        override fun validate(phoneNumber: String): Flow<UIValidationState> {
            return when {
                phoneNumber.isEmpty() -> {
                    flow {
                        emit(UIValidationState.IsEmpty("Please enter phone number"))
                    }
                }
                phoneNumber.length < 12 -> {
                    flow {
                        emit(UIValidationState.PhoneNumberLess("Phone number must contain at least 12 digits"))
                    }
                }
                else -> {
                    flow {
                        emit(UIValidationState.Success("validate"))
                    }
                }
            }
        }
    }
}