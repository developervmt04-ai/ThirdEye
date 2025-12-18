package com.example.thirdeye.biometrics


import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import com.example.thirdeye.R
import com.example.thirdeye.constants.Constants.LOCK_KEY
import com.example.thirdeye.data.localData.BiometricPrefs

class BiometricHelper(private val activity: FragmentActivity) {

    private val executor = ContextCompat.getMainExecutor(activity)


    fun isBiometricAvailable(): Boolean {
        val bm = BiometricManager.from(activity)
        return when (bm.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(activity, activity.getString(R.string.noBMHA), Toast.LENGTH_SHORT)
                    .show()
                false
            }

            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                Toast.makeText(activity, activity.getString(R.string.noBmh), Toast.LENGTH_SHORT)
                    .show()
                false
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.addFingerPrint),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

            else -> false

        }


    }

    fun authenticate(onSuccess: () -> Unit) {
        if (!isBiometricAvailable()) return
        val bmP =
            BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(activity, R.string.bioMetricError, Toast.LENGTH_SHORT).show()
                    activity.finish()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    activity.finish()
                }


            })
        val pi = BiometricPrompt.PromptInfo.Builder()
            .setTitle(activity.getString(R.string.unlock_app))
            .setSubtitle(activity.getString(R.string.biometricDescription))
            .setNegativeButtonText("Cancel")

            .build()
        bmP.authenticate(pi)


    }

    fun toggleAppLock(
        enable: Boolean,
        prefs: BiometricPrefs,
        onSwitchUpdated: (Boolean) -> Unit
    ) {
        if (!enable) {

            prefs.setBiometricKeyEnabled(false)
            onSwitchUpdated(false)
            return
        }

        if (!isBiometricAvailable()) {

            onSwitchUpdated(false)
            return
        }


        authenticate {
            prefs.setBiometricKeyEnabled(true)
            onSwitchUpdated(true)
        }
    }


}