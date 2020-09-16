package com.nikpnch.contacts.screens.contactsscreen

import android.util.Log
import com.nikpnch.contacts.base.BaseViewModel
import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.data.ContactsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactsViewModel(private val interactor: ContactsInteractor) : BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, emptyList())

    init {
        processDataEvent(DataEvent.RequestContacts)
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.RequestContacts -> {
                interactor
                    .getAllContacts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processUiEvent(DataEvent.SuccessContactRequested(it))
                        },
                        {
                            Log.d("debug", "FAIL DataEvent.RequestContacts")
                        }
                    )
            }

            is DataEvent.SuccessContactRequested -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
                    contactsList = event.listContactsModel
                )
            }
        }
        return null
    }
}