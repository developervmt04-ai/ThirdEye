package com.example.thirdeye.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.example.thirdeye.databinding.AudibleDialogBinding

class AudibleDialog(context: Context) {

   private val dialog= Dialog(context)
    private val binding= AudibleDialogBinding.inflate(LayoutInflater.from(context))

    init {
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        binding.closeButton.setOnClickListener {
            dialog.dismiss()

        }


    }
    fun setTitle(title:String): AudibleDialog{
        binding.title.text=title
        return this

    }
    fun setMessage(msg:String): AudibleDialog{
        binding.description.text=msg
        return this
    }
    fun onClick(action:()->Unit){
        binding.addWidget.setOnClickListener {
            action
            dialog.dismiss()
        }


    }
    fun show(){
        dialog.show()
    }



}