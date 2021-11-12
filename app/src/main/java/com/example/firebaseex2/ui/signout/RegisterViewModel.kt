package com.example.firebaseex2.ui.signout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseex2.domain.usecase.SignOutWthPhoneNumber
import com.example.firebaseex2.domain.usecase.UIValidationState
import com.example.firebaseex2.domain.usecase.ValidatePhone
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val useCaseValidatePhone: ValidatePhone,
    private val signOutWthPhoneNumber: SignOutWthPhoneNumber,
) : ViewModel() {

    private val _onCodeSent = MutableLiveData<String>().apply {
        value = ""
    }
    private val _uiValidateLiveData = MutableLiveData<UIValidationState>().apply {
        value = UIValidationState.Loading
    }
    val onCodeSent: LiveData<String> = _onCodeSent


    val uiValidateLiveData: LiveData<UIValidationState> = _uiValidateLiveData

    fun validate(phoneNumber: String) {
        viewModelScope.launch {
            useCaseValidatePhone.validate(phoneNumber = phoneNumber).collect {
                _uiValidateLiveData.value = it
            }
        }
    }

    fun onCodeSent() {
        viewModelScope.launch {
            signOutWthPhoneNumber.onCodeSent().collect { value ->
                _onCodeSent.value = value
            }
        }
    }

    fun authUser(phoneNumber: String) {
        viewModelScope.launch {
            signOutWthPhoneNumber.authUser(number = phoneNumber)
        }
    }
}