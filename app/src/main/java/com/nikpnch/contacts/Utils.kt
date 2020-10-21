package com.nikpnch.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

const val REQUEST_LOAD_IMAGE = 1
const val REQUEST_PERMISSION = 2
const val REQUEST_TAKE_PHOTO = 3
const val DATE_PATTERN = "yyyyMMdd_HHmmss"
const val FILES_AUTHORITY = "com.example.android.fileprovider"
const val IMAGE_MIME_TYPE = "image/*"

fun Fragment.checkPermissionForReadExternalStorage(): Boolean {
    val result = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    return result == PackageManager.PERMISSION_GRANTED
}

fun Fragment.requestPermissionForReadExternalStorage() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION)
    } else {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_PERMISSION
        )
    }
}