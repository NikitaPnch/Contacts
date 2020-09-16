package com.nikpnch.contacts.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikpnch.contacts.di.CONTACTS_TABLE

@Entity(tableName = CONTACTS_TABLE)
data class ContactEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String
)