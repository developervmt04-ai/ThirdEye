package com.example.thirdeye.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thirdeye.biometrics.BiometricHelper
import com.example.thirdeye.constants.Constants.LOCK_KEY
import com.example.thirdeye.constants.Constants.PREF_NAME
import com.example.thirdeye.databinding.FragmentSettingBinding


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
        val sharedpref=requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedState= sharedpref.getBoolean(LOCK_KEY,false)
        isChanged = true
        binding.bioMetricSwitch.isChecked = savedState
        isChanged = false

        binding.bioMetricSwitch.setOnCheckedChangeListener {_, isChecked ->
            val biometricHelper = BiometricHelper(requireActivity())

            if (isChanged) return@setOnCheckedChangeListener
            isChanged = true
            biometricHelper.toggleAppLock(isChecked,sharedpref,{switchState->
                isChanged = true
                binding.bioMetricSwitch.isChecked = switchState
                isChanged = false



            })



        }






    }


}