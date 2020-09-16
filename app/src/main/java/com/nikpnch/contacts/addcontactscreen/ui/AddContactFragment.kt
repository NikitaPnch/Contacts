package com.nikpnch.contacts.addcontactscreen.ui

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.nikpnch.contacts.R
import com.nikpnch.contacts.contactsscreen.ui.ContactsScreen
import com.nikpnch.contacts.di.CONTACTS_QUALIFIER
import kotlinx.android.synthetic.main.fragment_add_contact.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class AddContactFragment : Fragment(R.layout.fragment_add_contact) {

    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    companion object {
        private const val RESULT_LOAD_IMAGE = 1
        private const val REQUEST_PERMISSION = 2
        private const val IMAGE_MIME_TYPE = "image/*"
        private const val SELECTED_ID = "SELECTED_ID"

        fun newInstance(): AddContactFragment {
            return AddContactFragment()
        }

        fun newInstance(id: String): AddContactFragment {
            val bundle = Bundle()
            bundle.putString(SELECTED_ID, id)
            val fragment = AddContactFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var currentImagePath = ""
    private val viewModel: AddContactsViewModel by viewModel()
    private var isEditMode = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))

        val selectedId = arguments?.getString(SELECTED_ID)

        if (selectedId != null) {
            isEditMode = true
            viewModel.processUiEvent(UiEvent.OnRequestContact(selectedId))
        } else {
            setupGlide(currentImagePath)
        }

        ivAvatar.setOnClickListener {
            if (checkPermissionForReadExternalStorage()) {
                pickImage()
            } else {
                requestPermissionForReadExternalStorage()
            }
        }

        fabDone.setOnClickListener {
            if (isEditMode) {
                viewModel.processUiEvent(
                    UiEvent.OnEditContactClick(
                        selectedId!!,
                        currentImagePath,
                        etName.text.toString(),
                        etPhoneNumber.text.toString()
                    )
                )
            } else {
                viewModel.processUiEvent(
                    UiEvent.OnSaveContactClick(
                        currentImagePath,
                        etName.text.toString(),
                        etPhoneNumber.text.toString()
                    )
                )
            }
            router.navigateTo(ContactsScreen())
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
            }
            STATUS.CONTENT -> {
                val model = viewState.contactModel
                currentImagePath = model?.avatar ?: ""
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

    private fun checkPermissionForReadExternalStorage(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissionForReadExternalStorage() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_PERMISSION
        )
    }

    private fun setupGlide(imagePath: String) {
        Glide.with(this)
            .load(imagePath)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(ivAvatar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            currentImagePath = data.data.toString()
            setupGlide(currentImagePath)
        }
    }
}