package com.example.firebaseex2.di

import com.example.firebaseex2.data.SignOutRepositoryImpl
import com.example.firebaseex2.domain.repository.SignOutRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<SignOutRepository> {
        SignOutRepositoryImpl(signOutFirebase = get())
    }
}