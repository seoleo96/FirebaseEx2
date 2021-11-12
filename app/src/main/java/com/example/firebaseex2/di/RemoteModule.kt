package com.example.firebaseex2.di

import com.example.firebaseex2.MainActivity
import com.example.firebaseex2.data.remote.SignOutFirebase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val remoteModule = module{
    factory<SignOutFirebase> {
        SignOutFirebase.Base(context = MainActivity.instance!!)
    }
}
