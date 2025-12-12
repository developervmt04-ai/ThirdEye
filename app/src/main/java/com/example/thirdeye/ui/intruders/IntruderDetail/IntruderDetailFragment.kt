package com.example.thirdeye.ui.intruders.IntruderDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.thirdeye.R
import com.example.thirdeye.databinding.FragmentIntruderDetailBinding
import com.example.thirdeye.ui.intruders.IntruderPhotosViewModel
import kotlinx.coroutines.launch


class IntruderDetailFragment : Fragment() {
    private lateinit var binding: FragmentIntruderDetailBinding
    private val args: IntruderDetailFragmentArgs by navArgs()
    private val viewModel: IntruderPhotosViewModel by activityViewModels()


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

        binding.deleteIcon.setOnClickListener {
            lifecycleScope.launch {

                intruderData?.file?.let {file->
                    viewModel.deleteImage(file)
                    requireActivity().onBackPressed()
                }

            }
            binding.historyIcon.setOnClickListener {
               findNavController().navigate(R.id.action_intruderDetailFragment_to_historyFragment)



            }






        }






    }

}