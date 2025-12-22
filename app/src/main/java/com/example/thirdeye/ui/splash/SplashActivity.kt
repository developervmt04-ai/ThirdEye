package com.example.thirdeye.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.thirdeye.MainActivity
import com.example.thirdeye.R
import com.example.thirdeye.biometrics.BiometricHelper
import com.example.thirdeye.data.localData.BiometricPrefs

class SplashActivity : AppCompatActivity() {

    private var navigated = false

    private lateinit var biometricHelper: BiometricHelper
    private lateinit var biometricPrefs: BiometricPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        biometricHelper = BiometricHelper(this)
        biometricPrefs = BiometricPrefs(this)


        if (savedInstanceState != null) return
    }

    override fun onResume() {
        super.onResume()


        window.decorView.post {
            if (biometricPrefs.isBiometricEnabled()) {

                biometricHelper.authenticate(
                    onSuccess = { startMain() },
                    onCancel = { finish() }
                )
            } else {

                startMain()
            }
        }
    }

    private fun startMain() {
        if (navigated) return
        navigated = true
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
