package com.nikpnch.contacts.screens.contactsscreen

import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.screens.contactsscreen.ui.model.ContactsModel

data class ViewState(
    val status: STATUS,
    val contactsList: List<ContactsModel>
)

sealed class DataEvent() : Event {
    object RequestContacts : DataEvent()
    data class SuccessContactRequested(val listContactsModel: List<ContactsModel>) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}