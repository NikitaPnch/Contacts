package com.nikpnch.contacts.screens.addcontactscreen

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nikpnch.contacts.*
import kotlinx.android.synthetic.main.bottom_sheet_avatar.view.*
import kotlinx.android.synthetic.main.fragment_add_contact.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddContactFragment : Fragment(R.layout.fragment_add_contact) {

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
                showDialogActionAvatar()
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
            REQUEST_LOAD_IMAGE
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
                showDialogActionAvatar()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            currentImagePath = data.data.toString()
            setupGlide(currentImagePath)
        }
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setupGlide(currentImagePath)
        }
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(Date())
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentImagePath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        FILES_AUTHORITY,
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    private fun showDialogActionAvatar() {
        val mBottomSheetDialog = BottomSheetDialog(requireActivity())
        val sheetView: View =
            requireActivity().layoutInflater.inflate(R.layout.bottom_sheet_avatar, null)
        sheetView.actionCamera.setOnClickListener {
            dispatchTakePictureIntent()
            mBottomSheetDialog.dismiss()
        }
        sheetView.actionGallery.setOnClickListener {
            pickImage()
            mBottomSheetDialog.dismiss()
        }
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()
    }
}