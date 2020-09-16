package com.nikpnch.contacts.contactsscreen.ui

import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.contactsscreen.ui.model.ContactsModel

data class ViewState(
    val status: STATUS,
    val contactsList: List<ContactsModel>
)

sealed class UiEvent() : Event {
}

sealed class DataEvent() : Event {
    object RequestContacts : DataEvent()
    data class SuccessContactRequested(val listContactsModel: List<ContactsModel>) : DataEvent()
    data class SuccessContactSaved(val listContactsModel: List<ContactsModel>) : DataEvent()
    data class SuccessContactUpdated(val listContactsModel: List<ContactsModel>) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}