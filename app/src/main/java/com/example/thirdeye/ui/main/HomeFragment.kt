package com.example.thirdeye.ui.main
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.thirdeye.MainActivity
import com.example.thirdeye.R
import com.example.thirdeye.databinding.FragmentHomeBinding
import com.example.thirdeye.service.CameraCaptureService
import com.example.thirdeye.ui.dialogs.AddWidgetDialog
import com.example.thirdeye.ui.dialogs.AudibleDialog
import com.example.thirdeye.ui.intruders.IntruderPhotosViewModel
import com.example.thirdeye.ui.timer.TimerViewModel
import com.example.thirdeye.ui.widget.AddWidget
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.concurrent.timer


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var addWidgetDialog: AddWidgetDialog
    private lateinit var homeAdapter: HomePagerAdapter
    val viewModel: IntruderPhotosViewModel by activityViewModels()
    private var jobTimer: Job?=null
    private val timerViewModel: TimerViewModel by viewModels()


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
        observeTimer()
        homeAdapter = HomePagerAdapter()
        binding.homePager.adapter = homeAdapter





        viewModel.loadImages()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.images.collect {
                if (it.isEmpty()){

                    binding.emptyIntruders.visibility=View.VISIBLE
                    binding.homePager.visibility=View.INVISIBLE
                }
                else{


                    binding.emptyIntruders.visibility=View.INVISIBLE
                    binding.homePager.visibility=View.VISIBLE
                }

                homeAdapter.differ.submitList(it)


            }


        }

        homeAdapter.onLockedClick={
            lifecycleScope.launch {
                viewModel.unlockImage(it.id)
            }

        }
        homeAdapter.onWatchAdClicked={
            Toast.makeText(requireContext(),"Clicked on Watch Ad Button", Toast.LENGTH_SHORT).show()

        }
        homeAdapter.onPremiumClicked={
            Toast.makeText(requireContext(),"Premium Clicked", Toast.LENGTH_SHORT).show()

        }


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
            AudibleDialog(requireContext())
                .setTitle(getString(R.string.alarmtitle))
                .setMessage(getString(R.string.attempt))
                .onClick {
                    findNavController().navigate(R.id.action_homeFragment_to_alarmFragment)
                }
                .show()
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
        binding.addWidgetBtn.setOnClickListener {
            addWidgetDialog = AddWidgetDialog(requireContext())
            addWidgetDialog.setTitle(getString(R.string.addTitle))
                .setDescription(getString(R.string.add_widget)).onClick {
                    AddWidget.addWidget(requireContext())

                }.show()


        }

    }

    private fun observeTimer() {
        jobTimer=viewLifecycleOwner.lifecycleScope.launch {


            viewLifecycleOwner.lifecycleScope.launch {
                timerViewModel.secondLeft.collect {seconds->
                    val min=seconds/60
                    val sec=seconds%60
                    binding.tvTimer.text=String.format("%02d:%02d",min,sec)
                }

            }
        }
    }


}