package com.example.thirdeye.permissions

import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class Permissions(private val activity: AppCompatActivity) {

private val requiredPermissions=buildList {
add(android.Manifest.permission.CAMERA)
    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
        add(android.Manifest.permission.READ_MEDIA_IMAGES)
        add(android.Manifest.permission.POST_NOTIFICATIONS)
    }
    else{

add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}

    val requestLauncher=activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){

        permissions->
        permissions.forEach { (perm,granted)->
            handlePermissions(perm,granted)
        }



    }

    fun checkAndRequest(){
        val notGranted=requiredPermissions.filter {
            ContextCompat.checkSelfPermission(activity,it)!= PackageManager.PERMISSION_GRANTED
        }
        if (notGranted.isNotEmpty()){
            requestLauncher.launch(notGranted.toTypedArray())


        }


    }


    private fun handlePermissions(permission:String,granted: Boolean){
        if (!granted){
            when(permission){
                android.Manifest.permission.CAMERA->toast("Camera Permission Required")
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_EXTERNAL_STORAGE ->toast("Storage Permission Required")
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE ->toast("Storage Write Required")
                android.Manifest.permission.POST_NOTIFICATIONS ->toast("Notification Permission Needed")



            }


        }


    }
    private fun toast(msg:String){
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()


    }

}