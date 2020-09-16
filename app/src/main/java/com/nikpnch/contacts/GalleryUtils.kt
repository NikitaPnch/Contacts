package com.nikpnch.contacts

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val RESULT_LOAD_IMAGE = 1
const val REQUEST_PERMISSION = 2
const val IMAGE_MIME_TYPE = "image/*"

fun Context.checkPermissionForReadExternalStorage(): Boolean {
    val result = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    return result == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestPermissionForReadExternalStorage() {
    ActivityCompat.requestPermissions(
        this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
        REQUEST_PERMISSION
    )
}