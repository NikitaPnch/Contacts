package com.nikpnch.contacts.contactsscreen

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ContactsScreen : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return ContactsFragment.newInstance()
    }
}