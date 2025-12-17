package com.example.thirdeye.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import com.example.thirdeye.data.localData.IntruderSelfiePrefs

object AttemptsDialog {

    fun showAttemptsDialog(context: Context, onSelect: (Int) -> Unit) {
        val prefs = IntruderSelfiePrefs(context)
        val options = arrayOf("1", "2", "3", "4")


        val savedAttempts = prefs.getWrongAttempts()


        var selectedIndex = options.indexOf(savedAttempts.toString())
        if (selectedIndex == -1) selectedIndex = 0

        AlertDialog.Builder(context)
            .setTitle("Select number of attempts after which selfie will be taken")
            .setSingleChoiceItems(options, selectedIndex) { _, which ->
                selectedIndex = which
            }
            .setPositiveButton("OK") { dialog, _ ->
                val selectedItem = options[selectedIndex].toInt()
                prefs.setWrongTries(selectedItem) // save selection
                onSelect(selectedItem)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
