package com.example.thirdeye.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import com.example.thirdeye.databinding.GeneralDialogBinding

class GeneralDialog (context: Context){

    val dialog= Dialog(context)
    val binding= GeneralDialogBinding.inflate(LayoutInflater.from(context))
    init {
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)

        binding.closeButton.setOnClickListener {
            dialog.dismiss()


        }
    }

    fun setTitle(title:String): GeneralDialog{
        binding.title.text=title
        return this


    }
    fun setDescription(msg:String): GeneralDialog{
        binding.description.text=msg
        return  this


    }
    fun onClick(action:()-> Unit){
        binding.addWidget.setOnClickListener {
            action()
            dialog.dismiss()



        }
        fun show(){

            dialog.show()
        }


    }



}