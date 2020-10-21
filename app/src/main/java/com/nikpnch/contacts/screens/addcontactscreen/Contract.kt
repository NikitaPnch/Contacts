package com.nikpnch.contacts.screens.addcontactscreen

import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.screens.contactsscreen.model.ContactsModel

data class ViewState(
    val status: STATUS,
    val contactModel: ContactsModel?
)

sealed class UiEvent : Event {
    data class OnSaveContactClick(
        val image: String,
        val name: String,
        val phoneNumber: String
    ) : UiEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}