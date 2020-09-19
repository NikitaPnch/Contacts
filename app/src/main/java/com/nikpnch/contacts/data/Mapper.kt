package com.nikpnch.contacts.data

import com.nikpnch.contacts.data.local.ContactEntity
import com.nikpnch.contacts.screens.contactsscreen.model.ContactsModel

fun ContactEntity.mapToUiModel(): ContactsModel {
    return ContactsModel(id, imagePath, name, phoneNumber)
}

fun ContactsModel.mapToEntityModel(): ContactEntity {
    return ContactEntity(
        id = id,
        imagePath = imagePath,
        name = name,
        phoneNumber = phoneNumber
    )
}