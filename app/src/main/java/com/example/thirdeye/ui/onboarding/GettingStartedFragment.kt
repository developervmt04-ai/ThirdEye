package com.example.thirdeye.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thirdeye.R
import com.example.thirdeye.data.localData.SecurityPrefs
import com.example.thirdeye.databinding.FragmentGettingStartedBinding

class GettingStartedFragment : Fragment() {
    private lateinit var binding: FragmentGettingStartedBinding
    private lateinit var securityPre: SecurityPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentGettingStartedBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}