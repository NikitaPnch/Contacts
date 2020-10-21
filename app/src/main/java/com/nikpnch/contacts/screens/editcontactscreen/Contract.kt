package com.nikpnch.contacts.screens.editcontactscreen

import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.screens.contactsscreen.model.ContactsModel

data class ViewState(
    val status: STATUS,
    val contactModel: ContactsModel?
)

sealed class UiEvent : Event {
    data class OnUpdateContactClick(
        val image: String,
        val name: String,
        val phoneNumber: String
    ) : UiEvent()

    object OnRequestContact : UiEvent()
}

sealed class DataEvent : Event {
    data class SuccessContactRequested(val model: ContactsModel) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}