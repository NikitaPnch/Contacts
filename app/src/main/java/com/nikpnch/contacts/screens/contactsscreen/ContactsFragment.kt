package com.nikpnch.contacts.screens.contactsscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.nikpnch.contacts.R
import com.nikpnch.contacts.setAdapterAndCleanupOnDetachFromWindow
import com.nikpnch.contacts.setData
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    companion object {
        fun newInstance(): ContactsFragment {
            return ContactsFragment()
        }
    }

    private val viewModel: ContactsViewModel by viewModel()
    private val adapter = ListDelegationAdapter(
        contactsAdapterDelegate {
            viewModel.processUiEvent(UiEvent.OpenEditScreen(it))
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        viewModel.processUiEvent(UiEvent.RequestContacts)
        rvContacts.layoutManager = LinearLayoutManager(requireContext())
        rvContacts.setAdapterAndCleanupOnDetachFromWindow(adapter)
        fabAddContact.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OpenAddContactScreen)
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