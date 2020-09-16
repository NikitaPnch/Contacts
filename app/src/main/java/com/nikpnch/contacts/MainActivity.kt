package com.nikpnch.contacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikpnch.contacts.di.CONTACTS_QUALIFIER
import com.nikpnch.contacts.screens.contactsscreen.ContactsScreen
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity() {

    private val navigator: Navigator by lazy { createNavigator() }
    private val router: Router by inject(named(CONTACTS_QUALIFIER))
    private val navigatorHolder: NavigatorHolder by inject(named(CONTACTS_QUALIFIER))

    private fun createNavigator(): Navigator {
        return SupportAppNavigator(this, R.id.frameFragmentContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router.newRootScreen(ContactsScreen())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}