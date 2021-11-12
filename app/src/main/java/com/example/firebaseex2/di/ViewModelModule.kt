package com.example.firebaseex2.di

import com.example.firebaseex2.ui.signout.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<RegisterViewModel> {
        RegisterViewModel(
            useCaseValidatePhone = get(),
            signOutWthPhoneNumber = get()
        )
    }
}