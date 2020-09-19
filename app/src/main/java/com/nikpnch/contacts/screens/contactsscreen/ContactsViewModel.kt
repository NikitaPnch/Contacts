package com.nikpnch.contacts.screens.contactsscreen

import android.util.Log
import com.nikpnch.contacts.base.BaseViewModel
import com.nikpnch.contacts.base.Event
import com.nikpnch.contacts.data.ContactsInteractor
import com.nikpnch.contacts.screens.addcontactscreen.AddContactScreen
import com.nikpnch.contacts.screens.editcontactscreen.EditContactScreen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router

class ContactsViewModel(private val interactor: ContactsInteractor, private val router: Router) :
    BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, emptyList())

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.RequestContacts -> {
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

            is UiEvent.OpenAddContactScreen -> {
                router.navigateTo(AddContactScreen())
            }

            is UiEvent.OpenEditScreen -> {
                router.navigateTo(EditContactScreen(previousState.contactsList[event.index].id))
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