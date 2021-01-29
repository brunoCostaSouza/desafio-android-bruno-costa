package com.marvel_api_bruno_costa.arch

import android.app.Application
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class MyApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(netWorkModule, presenterModule))
        }
    }
}