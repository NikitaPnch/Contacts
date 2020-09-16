package com.nikpnch.contacts.addcontactscreen.ui

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class AddContactScreen : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return AddContactFragment.newInstance()
    }
}