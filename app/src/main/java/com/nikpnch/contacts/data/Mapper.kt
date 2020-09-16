package com.nikpnch.contacts.data

import com.nikpnch.contacts.contactsscreen.ui.model.ContactsModel
import com.nikpnch.contacts.data.local.ContactEntity

fun ContactEntity.mapToUiModel(): ContactsModel {
    return ContactsModel(id, image, name, phoneNumber)
}

fun ContactsModel.mapToEntityModel(): ContactEntity {
    return ContactEntity(
        id = id,
        image = avatar,
        name = name,
        phoneNumber = phoneNumber
    )
}