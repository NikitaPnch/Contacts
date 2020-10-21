package com.nikpnch.contacts.screens.editcontactscreen

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class EditContactScreen(private val id: String) : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return EditContactFragment.newInstance(id)
    }
}