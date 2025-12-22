package com.example.thirdeye.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thirdeye.MainActivity
import com.example.thirdeye.R
import com.example.thirdeye.data.localData.SecurityPrefs
import com.example.thirdeye.databinding.FragmentGettingStartedBinding

class GettingStartedFragment : Fragment() {
    private lateinit var binding: FragmentGettingStartedBinding
    private lateinit var securityPre: SecurityPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        securityPre= SecurityPrefs(requireContext())

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

        binding.btnNext.setOnClickListener {
            securityPre.isFirstLaunch = false
            (requireActivity() as MainActivity).requestPermissions()
            findNavController().navigate(R.id.action_gettingStartedFragment_to_homeFragment)
        }



    }

}