package com.nikpnch.contacts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao
}