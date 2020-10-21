package com.nikpnch.contacts.data

import com.nikpnch.contacts.data.local.ContactEntity
import com.nikpnch.contacts.data.local.ContactsDao

class ContactsRepositoryImpl(private val contactsDao: ContactsDao) : ContactsRepository {
    override fun saveContact(entity: ContactEntity) = contactsDao.create(entity)

    override fun updateContact(entity: ContactEntity) = contactsDao.update(entity)

    override fun getAllContacts() = contactsDao.read().map { list ->
        list.map { it.mapToUiModel() }
    }

    override fun getContact(id: String) = contactsDao.getContact(id).map {
        it.mapToUiModel()
    }
}