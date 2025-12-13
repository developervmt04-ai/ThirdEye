package com.example.thirdeye.ui.camouflage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thirdeye.R
import com.example.thirdeye.databinding.FragmentCamouflageBinding


class CamouflageFragment : Fragment() {
    private lateinit var binding: FragmentCamouflageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCamouflageBinding.inflate(layoutInflater,container,false)

        return binding.root
    }


}