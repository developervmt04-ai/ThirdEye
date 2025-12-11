package com.example.thirdeye.ui.intruders

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thirdeye.R
import com.example.thirdeye.data.encryptedStorage.EncryptedStorageRepository
import com.example.thirdeye.data.models.IntrudersImages
import com.example.thirdeye.databinding.FragmentIntrudersBinding
import com.example.thirdeye.ui.dialogs.FingerPrintDialog


class IntrudersFragment : Fragment() {
    private lateinit var binding: FragmentIntrudersBinding
    private lateinit var intruderImageAdapter: IntruderImageAdapter
    private val viewModel: IntruderPhotosViewModel by activityViewModels ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentIntrudersBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backIcon.setOnClickListener {

            findNavController().navigateUp()
        }


        setUpIntruderRv()

        intruderImageAdapter.onClick={it->
            val actions= IntrudersFragmentDirections.actionIntrudersFragmentToIntruderDetailFragment(it)
            findNavController().navigate(actions)

        }
        intruderImageAdapter.onLockedClick ={
            val dialog=FingerPrintDialog(requireContext())
            dialog.setTitle("Open this First").setMessage("Picture will be blur until u watch add or buy subscription ")
                .show()



        }

        lifecycleScope.launchWhenStarted {
            viewModel.images.collect { list ->
                intruderImageAdapter.differ.submitList(list)
            }
        }

        viewModel.loadImages()
    }









    private fun setUpIntruderRv() {
        intruderImageAdapter= IntruderImageAdapter()
        binding.intruderRv.layoutManager= GridLayoutManager(requireActivity(),3)
        binding.intruderRv.adapter=intruderImageAdapter

    }


}