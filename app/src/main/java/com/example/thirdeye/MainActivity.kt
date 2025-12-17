package com.example.thirdeye

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.example.thirdeye.databinding.ActivityMainBinding
import com.example.thirdeye.permissions.DeviceAdminManager
import com.example.thirdeye.permissions.Permissions
import com.example.thirdeye.service.CameraCaptureService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var permissions: Permissions
    private lateinit var deviceAdminManager: DeviceAdminManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        deviceAdminManager= DeviceAdminManager(this)
         permissions= Permissions(this)
        permissions.checkAndRequest()

        if (!deviceAdminManager.isDeviceAdminActive()){

            deviceAdminManager.requestDeviceAdmin()
        }

        permissions.checkAndRequest()
    }

    override fun onResume() {
        super.onResume()
        CameraCaptureService.Instance?.stopAlarm()

    }



}