package com.nikpnch.contacts.data

import com.nikpnch.contacts.data.local.ContactEntity
import com.nikpnch.contacts.screens.contactsscreen.ui.model.ContactsModel
import io.reactivex.Single

interface ContactsRepository {

    fun saveContact(entity: ContactEntity): Single<Unit>

    fun updateContact(entity: ContactEntity): Single<Unit>

    fun getAllContacts(): Single<List<ContactsModel>>

    fun getContact(id: String): Single<ContactsModel>
}