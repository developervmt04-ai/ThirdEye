package com.example.thirdeye.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.thirdeye.MainActivity
import com.example.thirdeye.R
import com.example.thirdeye.biometrics.BiometricHelper
import com.example.thirdeye.data.localData.BiometricPrefs
import com.example.thirdeye.ui.dialogs.biometricDialogs.UnlockDialog

class SplashActivity : AppCompatActivity() {

    private var navigated = false

    private lateinit var biometricHelper: BiometricHelper
    private lateinit var biometricPrefs: BiometricPrefs
    private lateinit var dialog: UnlockDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        biometricHelper = BiometricHelper(this)
        biometricPrefs = BiometricPrefs(this)
        dialog = UnlockDialog(this)
    }

    override fun onResume() {
        super.onResume()

        if (biometricPrefs.isBiometricEnabled()) {
            showUnlockDialog()
        } else {
            startMain()
        }
    }

    private fun showUnlockDialog() {
        dialog.setTitle(getString(R.string.Fingerprint_lock_text))
            .setDescription(getString(R.string.safa))
            .onClick {
                biometricHelper.authenticate(
                    onSuccess = { startMain() },
                    onCancel = {
                        finishAffinity()
                    }
                )
            }
            .show()
    }

    private fun startMain() {
        if (navigated) return
        navigated = true
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

