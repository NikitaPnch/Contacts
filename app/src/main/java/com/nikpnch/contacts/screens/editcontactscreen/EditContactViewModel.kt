package com.nikpnch.contacts.screens.editcontactscreen

import android.util.Log
import com.nikpnch.contacts.base.BaseViewModel
import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.data.ContactsInteractor
import com.nikpnch.contacts.screens.contactsscreen.model.ContactsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditContactViewModel(private val interactor: ContactsInteractor) :
    BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, null)

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnRequestContact -> {
                interactor.getContact(event.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.SuccessContactRequested(it))
                        },
                        {
                            Log.d(
                                "debug",
                                "FAIL UiEvent.OnRequestContact ${it.localizedMessage}"
                            )
                        }
                    )
            }

            is UiEvent.OnUpdateContactClick -> {
                interactor.updateContact(
                    ContactsModel(
                        event.id,
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
                                "debug:",
                                "SUCCESS UiEvent.OnEditContactClick"
                            )
                        },
                        {
                            Log.d(
                                "debug:",
                                "FAIL UiEvent.OnEditContactClick ${it.localizedMessage}"
                            )
                        }
                    )
            }

            is DataEvent.SuccessContactRequested -> {
                return previousState.copy(status = STATUS.CONTENT, contactModel = event.model)
            }
        }
        return null
    }
}