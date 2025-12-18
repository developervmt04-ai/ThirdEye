package com.example.thirdeye.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.thirdeye.biometrics.BiometricHelper
import com.example.thirdeye.databinding.FragmentSettingBinding
import com.example.thirdeye.data.localData.BiometricPrefs
import com.example.thirdeye.data.localData.IntruderSelfiePrefs
import com.example.thirdeye.ui.dialogs.AttemptsDialog


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

        binding.backIcon.setOnClickListener {

            findNavController().navigateUp()
        }

        val biometricPref= BiometricPrefs(requireContext())

        val biometricHelper = BiometricHelper(requireActivity())
        val selfiePrefs= IntruderSelfiePrefs(requireContext())







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
        binding.selfieAttempt.setOnClickListener {
            AttemptsDialog.showAttemptsDialog(requireContext()){
                selectNumber->
                selfiePrefs.setWrongTries(selectNumber)
                val tires=selfiePrefs.getWrongAttempts()
                binding.selfieDescription.text="Selfie will be taken after $tires wrong tries"





            }






        }







    }


}