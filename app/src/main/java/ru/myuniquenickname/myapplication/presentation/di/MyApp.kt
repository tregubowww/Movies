package ru.myuniquenickname.myapplication.presentation.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(appModule, viewModel, apiModule, netModule, databaseModule, repositoryModule))
        }
    }
}
