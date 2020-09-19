package com.nikpnch.contacts.data

import com.nikpnch.contacts.screens.contactsscreen.model.ContactsModel

class ContactsInteractor(private val repository: ContactsRepository) {
    fun getAllContacts() = repository.getAllContacts()

    fun saveContact(contact: ContactsModel) = repository.saveContact(contact.mapToEntityModel())

    fun getContact(id: String) = repository.getContact(id)

    fun updateContact(contact: ContactsModel) = repository.updateContact(contact.mapToEntityModel())
}