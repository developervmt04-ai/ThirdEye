package com.example.thirdeye.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thirdeye.biometrics.BiometricHelper
import com.example.thirdeye.databinding.FragmentSettingBinding
import com.example.thirdeye.helper.BiometricPrefs


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private var isChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSettingBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val biometricPref= BiometricPrefs(requireContext())

        val biometricHelper = BiometricHelper(requireActivity())







        isChanged = true
        binding.bioMetricSwitch.isChecked =biometricPref.isBiometricEnabled()
        isChanged = false

        binding.bioMetricSwitch.setOnCheckedChangeListener {_, isChecked ->

            if (isChanged) return@setOnCheckedChangeListener
            isChanged = true

            biometricHelper.toggleAppLock(isChecked,biometricPref,{switchState->
                isChanged = true
                binding.bioMetricSwitch.isChecked = switchState
                isChanged = false



            })



        }






    }


}