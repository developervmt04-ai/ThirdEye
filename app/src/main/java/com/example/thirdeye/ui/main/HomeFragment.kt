package com.example.thirdeye.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thirdeye.MainActivity
import com.example.thirdeye.R
import com.example.thirdeye.databinding.FragmentHomeBinding
import com.example.thirdeye.service.CameraCaptureService
import com.example.thirdeye.ui.dialogs.FingerPrintDialog
import com.example.thirdeye.ui.dialogs.permissionsDialog.PermissionDialog


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var isOn: Boolean = false

        binding.powerButton.setOnClickListener {
            isOn = !isOn;

            if (isOn) {

                binding.outerRing.setBackgroundResource(R.drawable.ring_on)
                binding.powerButton.setCardBackgroundColor(Color.parseColor("#00E5FF"))
                binding.powerIcon.setColorFilter(Color.parseColor("#00E5FF"))
                CameraCaptureService.start(requireContext())
            } else {

                binding.outerRing.setBackgroundResource(R.drawable.ring_off)
                binding.powerButton.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.powerIcon.setColorFilter(Color.parseColor("#FFFFFF"))
            }
        }

        binding.alarmBtn.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_alarmFragment)
        }

        binding.settingIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)

        }
        binding.intruderDropdown.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_intrudersFragment)

        }
        binding.camouflageIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_camouflageFragment)


        }

    }


}