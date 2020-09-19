package com.nikpnch.contacts.screens.editcontactscreen

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.nikpnch.contacts.*
import com.nikpnch.contacts.di.CONTACTS_QUALIFIER
import com.nikpnch.contacts.screens.contactsscreen.ContactsScreen
import kotlinx.android.synthetic.main.fragment_add_contact.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class EditContactFragment : Fragment(R.layout.fragment_add_contact) {

    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    companion object {
        private const val SELECTED_ID = "SELECTED_ID"

        fun newInstance(id: String): EditContactFragment {
            val bundle = Bundle()
            bundle.putString(SELECTED_ID, id)
            val fragment = EditContactFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var currentImagePath = ""
    private val viewModel: EditContactViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))

        val selectedId = arguments?.getString(SELECTED_ID)

        viewModel.processUiEvent(UiEvent.OnRequestContact(selectedId!!))

        ivAvatar.setOnClickListener {
            if (checkPermissionForReadExternalStorage()) {
                pickImage()
            } else {
                requestPermissionForReadExternalStorage()
            }
        }

        fabDone.setOnClickListener {
            viewModel.processUiEvent(
                UiEvent.OnUpdateContactClick(
                    selectedId,
                    currentImagePath,
                    etName.text.toString(),
                    etPhoneNumber.text.toString()
                )
            )
            router.backTo(ContactsScreen())
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
            }
            STATUS.CONTENT -> {
                val model = viewState.contactModel
                currentImagePath = model?.imagePath ?: ""
                setupGlide(currentImagePath)
                etName.setText(model?.name)
                etPhoneNumber.setText(model?.phoneNumber)
            }
            STATUS.ERROR -> {
            }
        }
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

    private fun setupGlide(imagePath: String) {
        Glide.with(this)
            .load(imagePath)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(ivAvatar)
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
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            currentImagePath = data.data.toString()
            setupGlide(currentImagePath)
        }
    }
}