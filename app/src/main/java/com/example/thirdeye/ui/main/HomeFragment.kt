package com.example.thirdeye.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thirdeye.R
import com.example.thirdeye.databinding.FragmentHomeBinding
import com.example.thirdeye.ui.dialogs.FingerPrintDialog


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FingerPrintDialog(requireContext())
            .setTitle("Enable Now").setMessage("Enable fingerprint to protect your app from unauthorized access").onOk {



            }.show()
    }



}