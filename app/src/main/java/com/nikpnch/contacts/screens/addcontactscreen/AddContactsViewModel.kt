package com.nikpnch.contacts.screens.addcontactscreen

import android.util.Log
import com.nikpnch.contacts.base.BaseViewModel
import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.data.ContactsInteractor
import com.nikpnch.contacts.screens.contactsscreen.model.ContactsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class AddContactsViewModel(private val interactor: ContactsInteractor) :
    BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, null)

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnSaveContactClick -> {
                interactor.saveContact(
                    ContactsModel(
                        UUID.randomUUID().toString(),
                        event.image,
                        event.name,
                        event.phoneNumber
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Log.d(
                                "debug",
                                "SUCCESS UiEvent.OnSaveContactClick"
                            )
                        },
                        {
                            Log.d(
                                "debug",
                                "FAIL UiEvent.OnSaveContactClick ${it.localizedMessage}"
                            )
                        }
                    )
            }
        }
        return null
    }
}