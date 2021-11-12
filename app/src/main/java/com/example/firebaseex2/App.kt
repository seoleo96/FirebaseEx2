package com.example.firebaseex2

import android.app.Activity
import android.app.Application
import com.example.firebaseex2.di.domainModule
import com.example.firebaseex2.di.remoteModule
import com.example.firebaseex2.di.repositoryModule
import com.example.firebaseex2.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.INFO)
            androidContext(androidContext = applicationContext)
            modules(
                remoteModule,
                repositoryModule,
                domainModule,
                viewModelModule
            )
        }
    }
}