package com.nikpnch.contacts.di

import androidx.room.Room
import com.nikpnch.contacts.addcontactscreen.AddContactsViewModel
import com.nikpnch.contacts.contactsscreen.ContactsViewModel
import com.nikpnch.contacts.data.ContactsInteractor
import com.nikpnch.contacts.data.ContactsRepository
import com.nikpnch.contacts.data.ContactsRepositoryImpl
import com.nikpnch.contacts.data.local.ContactsDatabase
import com.nikpnch.contacts.editcontactscreen.EditContactViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

const val CONTACTS_TABLE = "CONTACTS_TABLE"
const val CONTACTS_QUALIFIER = "CONTACTS_QUALIFIER"

val contactsModule = module {
    single {
        Room
            .databaseBuilder(androidContext(), ContactsDatabase::class.java, CONTACTS_TABLE)
            .build()
    }

    single {
        get<ContactsDatabase>().contactsDao()
    }

    single<ContactsRepository> {
        ContactsRepositoryImpl(get())
    }

    single {
        ContactsInteractor(get())
    }

    viewModel {
        ContactsViewModel(get())
    }

    viewModel {
        AddContactsViewModel(get())
    }

    viewModel {
        EditContactViewModel(get())
    }
}

val navModule = module {

    single<Cicerone<Router>>(named(CONTACTS_QUALIFIER)) {
        Cicerone.create()
    }

    single<NavigatorHolder>(named(CONTACTS_QUALIFIER)) {
        get<Cicerone<Router>>(named(CONTACTS_QUALIFIER)).navigatorHolder
    }

    single<Router>(named(CONTACTS_QUALIFIER)) {
        get<Cicerone<Router>>(named(CONTACTS_QUALIFIER)).router
    }
}