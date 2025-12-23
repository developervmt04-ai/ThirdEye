package com.example.thirdeye.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.example.thirdeye.R
import com.example.thirdeye.data.localData.IntruderSelfiePrefs

object AttemptsDialog {

    fun showAttemptsDialog(context: Context, onSelect: (Int) -> Unit) {

        val prefs = IntruderSelfiePrefs(context)
        val savedAttempts = prefs.getWrongAttempts()

        val view = LayoutInflater.from(context).inflate(R.layout.attempts_dialog, null)
        val radioGroup = view.findViewById<RadioGroup>(R.id.attemptsRadioGroup)
        val imageView= view.findViewById<ImageView>(R.id.backBtn)



        val options = listOf(1, 2, 3, 4)
        options.forEach { number ->
            val radioButton = RadioButton(context).apply {
                text = "  $number Wrong Attempts"
                id = number
                isChecked = number == savedAttempts
                setTextColor(context.getColor(R.color.black))
                buttonTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.BLACK)

            }

            radioGroup.addView(radioButton)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            prefs.setWrongTries(checkedId)
            onSelect(checkedId)
            dialog.dismiss()
        }

        dialog.show()
        imageView.setOnClickListener {
            dialog.dismiss()


        }
    }

}
