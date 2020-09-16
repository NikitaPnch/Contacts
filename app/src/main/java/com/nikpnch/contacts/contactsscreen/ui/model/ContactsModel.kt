package com.nikpnch.contacts.contactsscreen.ui.model

import com.nikpnch.contacts.Item

data class ContactsModel(
    val id: String,
    val avatar: String,
    val name: String,
    val phoneNumber: String
) : Item