package com.example.firebaseex2.di

import com.example.firebaseex2.domain.usecase.SignOutWthPhoneNumber
import com.example.firebaseex2.domain.usecase.ValidatePhone
import org.koin.dsl.module

val domainModule = module {
    factory<SignOutWthPhoneNumber> {
        SignOutWthPhoneNumber(signOutRepository = get())
    }

    factory<ValidatePhone> {
        ValidatePhone.Base()
    }
}