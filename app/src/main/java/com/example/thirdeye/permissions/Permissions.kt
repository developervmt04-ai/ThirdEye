package com.example.thirdeye.permissions

import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.thirdeye.ui.dialogs.permissionsDialog.PermissionDialog

class Permissions(private val activity: AppCompatActivity) {

    val requiredPermissions = buildList {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(android.Manifest.permission.POST_NOTIFICATIONS)
            add(android.Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            add(android.Manifest.permission.CAMERA)
            add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }


    private val singlePermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->

        }


    fun checkAndRequest() {
        val notGranted = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }

        if (notGranted.isNotEmpty()) {

            PermissionDialog(
                pendingPermissions = notGranted,
                onRequest = { perm ->
                    requestSinglePermission(perm)
                }
            ).show(activity.supportFragmentManager, "perm_dialog")
        }
    }

    fun requestSinglePermission(permission: String) {
        singlePermissionLauncher.launch(permission)
    }


}


