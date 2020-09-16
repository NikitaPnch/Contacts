package com.nikpnch.contacts.addcontactscreen.ui

import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.contactsscreen.ui.model.ContactsModel

data class ViewState(
    val status: STATUS,
    val contactModel: ContactsModel?
)

sealed class UiEvent() : Event {
    data class OnSaveContactClick(
        val image: String,
        val name: String,
        val phoneNumber: String
    ) : UiEvent()

    data class OnEditContactClick(
        val id: String,
        val image: String,
        val name: String,
        val phoneNumber: String
    ) : UiEvent()

    data class OnRequestContact(
        val id: String
    ) : UiEvent()
}

sealed class DataEvent() : Event {
    object SuccessContactUpdated : DataEvent()
    data class SuccessContactRequested(val model: ContactsModel) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}