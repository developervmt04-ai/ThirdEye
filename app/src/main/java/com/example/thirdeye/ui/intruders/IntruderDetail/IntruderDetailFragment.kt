package com.example.thirdeye.ui.intruders.IntruderDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.thirdeye.R
import com.example.thirdeye.databinding.FragmentIntruderDetailBinding


class IntruderDetailFragment : Fragment() {
    private lateinit var binding: FragmentIntruderDetailBinding
    private val args: IntruderDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentIntruderDetailBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intruderData=args.images
        binding.image.setImageBitmap(intruderData?.bitmap)




    }

}