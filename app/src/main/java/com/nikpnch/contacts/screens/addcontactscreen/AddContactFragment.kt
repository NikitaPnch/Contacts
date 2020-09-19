package com.nikpnch.contacts.screens.addcontactscreen

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nikpnch.contacts.*
import com.nikpnch.contacts.di.CONTACTS_QUALIFIER
import com.nikpnch.contacts.screens.contactsscreen.ContactsScreen
import kotlinx.android.synthetic.main.fragment_add_contact.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class AddContactFragment : Fragment(R.layout.fragment_add_contact) {

    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    companion object {
        fun newInstance(): AddContactFragment {
            return AddContactFragment()
        }
    }

    private var currentImagePath = ""
    private val viewModel: AddContactsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGlide(currentImagePath)

        ivAvatar.setOnClickListener {
            if (checkPermissionForReadExternalStorage()) {
                pickImage()
            } else {
                requestPermissionForReadExternalStorage()
            }
        }

        fabDone.setOnClickListener {
            viewModel.processUiEvent(
                UiEvent.OnSaveContactClick(
                    currentImagePath,
                    etName.text.toString(),
                    etPhoneNumber.text.toString()
                )
            )
            router.backTo(ContactsScreen())
        }
    }

    private fun setupGlide(imagePath: String) {
        Glide.with(this)
            .load(imagePath)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(ivAvatar)
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = IMAGE_MIME_TYPE
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, getString(R.string.pick_image)),
            RESULT_LOAD_IMAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            currentImagePath = data.data.toString()
            setupGlide(currentImagePath)
        }
    }
}