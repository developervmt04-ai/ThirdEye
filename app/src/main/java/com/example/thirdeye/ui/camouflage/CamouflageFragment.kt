package com.example.thirdeye.ui.camouflage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thirdeye.R
import com.example.thirdeye.constants.Constants.CALCULATOR_ICON
import com.example.thirdeye.constants.Constants.COMPASS_ICON
import com.example.thirdeye.constants.Constants.DEFAULT_ICON
import com.example.thirdeye.constants.Constants.NOTES_ICON
import com.example.thirdeye.constants.Constants.WEATHER_ICON
import com.example.thirdeye.data.localData.AppIcons
import com.example.thirdeye.databinding.FragmentCamouflageBinding


class CamouflageFragment : Fragment() {
    private lateinit var binding: FragmentCamouflageBinding
    private lateinit var iconsAdapter: IconsAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()

        }
        val icons=listOf(
            AppIcons(R.mipmap.ic_launcher,DEFAULT_ICON,"Default Icon"),
            AppIcons(R.drawable.weathericon,WEATHER_ICON,"Weather Icon"),
            AppIcons(R.drawable.calculatoricon,CALCULATOR_ICON,"Calculator Icon"),
            AppIcons(R.drawable.notesicon,NOTES_ICON,"Notes Icon"),
            AppIcons(R.drawable.compass,COMPASS_ICON,"Compass Icon"),
        )
        setupRv()
        iconsAdapter.differ.submitList(icons)

        iconsAdapter.onClick={
            IconChanger.changeIcon(requireContext(),it.alias)


        }

    }

    private fun setupRv() {
        iconsAdapter= IconsAdapter()
        binding.iconRV.layoutManager= GridLayoutManager(requireContext(),3)
        binding.iconRV.adapter=iconsAdapter


    }


}