package com.nikpnch.contacts.data.local

import androidx.room.*
import com.nikpnch.contacts.di.CONTACTS_TABLE
import io.reactivex.Single

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(entity: ContactEntity): Single<Unit>

    @Query("SELECT * FROM $CONTACTS_TABLE")
    fun read(): Single<List<ContactEntity>>

    @Update
    fun update(entity: ContactEntity): Single<Unit>

    @Delete
    fun delete(entity: ContactEntity): Single<Unit>

    @Query("SELECT * FROM $CONTACTS_TABLE WHERE id = :id")
    fun getContact(id: String): Single<ContactEntity>
}