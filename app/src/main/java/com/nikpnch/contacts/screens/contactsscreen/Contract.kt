package com.nikpnch.contacts.screens.contactsscreen

import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.screens.contactsscreen.model.ContactsModel

data class ViewState(
    val status: STATUS,
    val contactsList: List<ContactsModel>
)

sealed class UiEvent : Event {
    object RequestContacts : UiEvent()
}

sealed class DataEvent : Event {
    data class SuccessContactRequested(val listContactsModel: List<ContactsModel>) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}