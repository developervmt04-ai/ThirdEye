package com.example.thirdeye.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.example.thirdeye.databinding.NoInternetDialogBinding

class NoInternetDialog(context: Context) {
    val dialog= Dialog(context)
    val binding= NoInternetDialogBinding.inflate(LayoutInflater.from(context))
    init {
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)


    }
    fun setTitle(title:String): NoInternetDialog{
        binding.titleText.text=title
        return this
    }
    fun setMsg(msg:String): NoInternetDialog{
        binding.descriptionText.text=msg
        return this
    }
    fun onCheckInternetClick(action:()->Unit): NoInternetDialog{
        binding.checkInternetBtn.setOnClickListener {
            action.invoke()
            dialog.dismiss()

        }
        return this
    }
    fun onTryAgainClick(action: () -> Unit): NoInternetDialog{
        binding.tryAgain.setOnClickListener {
            action.invoke()
            dialog.dismiss()
        }
        return this


    }
}
