package com.nikpnch.contacts.contactsscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.nikpnch.contacts.R
import com.nikpnch.contacts.addcontactscreen.AddContactScreen
import com.nikpnch.contacts.di.CONTACTS_QUALIFIER
import com.nikpnch.contacts.editcontactscreen.EditContactScreen
import com.nikpnch.contacts.setAdapterAndCleanupOnDetachFromWindow
import com.nikpnch.contacts.setData
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    companion object {
        fun newInstance(): ContactsFragment {
            return ContactsFragment()
        }
    }

    private val router: Router by inject(named(CONTACTS_QUALIFIER))
    private val viewModel: ContactsViewModel by viewModel()
    private val adapter = ListDelegationAdapter(
        contactsAdapterDelegate {
            val contactsList = viewModel.viewState.value!!.contactsList
            router.navigateTo(EditContactScreen(contactsList[it].id))
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        rvContacts.layoutManager = LinearLayoutManager(requireContext())
        rvContacts.setAdapterAndCleanupOnDetachFromWindow(adapter)
        fabAddContact.setOnClickListener {
            router.navigateTo(AddContactScreen())
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
            }
            STATUS.CONTENT -> adapter.setData(viewState.contactsList)
            STATUS.ERROR -> {
            }
        }
    }
}