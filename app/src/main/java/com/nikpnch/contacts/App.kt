package com.nikpnch.contacts

import android.app.Application
import com.nikpnch.contacts.di.contactsModule
import com.nikpnch.contacts.di.navModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(contactsModule, navModule)
        }
    }
}