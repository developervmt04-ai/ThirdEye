package com.example.thirdeye.ui.main
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.thirdeye.MainActivity
import com.example.thirdeye.R
import com.example.thirdeye.data.localData.ButtonPrefs
import com.example.thirdeye.databinding.FragmentHomeBinding
import com.example.thirdeye.service.CameraCaptureService
import com.example.thirdeye.ui.dialogs.AddWidgetDialog
import com.example.thirdeye.ui.dialogs.AudibleDialog
import com.example.thirdeye.ui.intruders.IntruderPhotosViewModel
import com.example.thirdeye.ui.timer.TimerViewModel
import com.example.thirdeye.ui.widget.AddWidget
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.logging.Handler
import kotlin.concurrent.timer

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var addWidgetDialog: AddWidgetDialog
    private lateinit var homeAdapter: HomePagerAdapter
    val viewModel: IntruderPhotosViewModel by activityViewModels()
    private var jobTimer: Job? = null
    private val timerViewModel: TimerViewModel by viewModels()
    private lateinit var btnPrefs: ButtonPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnPrefs = ButtonPrefs(requireContext())

        observeTimer()

        homeAdapter = HomePagerAdapter()

        binding.homePager.adapter = homeAdapter

        viewModel.loadImages()

        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.images.collect { images ->

                if (images.isEmpty()) {

                    binding.emptyIntruders.visibility = View.VISIBLE

                    binding.homePager.visibility = View.INVISIBLE
                } else {

                    binding.emptyIntruders.visibility = View.INVISIBLE

                    binding.homePager.visibility = View.VISIBLE
                }
                homeAdapter.differ.submitList(images)
            }
        }

        homeAdapter.onLockedClick = { lifecycleScope.launch { viewModel.unlockImage(it.id) } }



        homeAdapter.onDetailsClicked = {
            val action = HomeFragmentDirections.actionHomeFragmentToIntruderDetailFragment(it)
            findNavController().navigate(action)
        }

        homeAdapter.onWatchAdClicked = {
            Toast.makeText(requireContext(), "Clicked on Watch Ad Button", Toast.LENGTH_SHORT).show()
        }


        homeAdapter.onPremiumClicked = {
            Toast.makeText(requireContext(), "Premium Clicked", Toast.LENGTH_SHORT).show()
        }



        var isOn = btnPrefs.isButtonEnabled() // restore previous state
        val handler = android.os.Handler(Looper.getMainLooper())

// Restore UI state
        if (isOn) {
            binding.powerButton.setCardBackgroundColor(Color.parseColor("#00E5FF"))
            binding.powerIcon.setColorFilter(Color.parseColor("#00E5FF"))
            binding.outerRing.progress = 100
        } else {
            binding.powerButton.setCardBackgroundColor(Color.WHITE)
            binding.powerIcon.setColorFilter(Color.WHITE)
            binding.outerRing.progress = 0
        }

        binding.powerButton.setOnClickListener {
            if (!isOn) {

                isOn = true
                btnPrefs.enableButton(true)
                binding.powerButton.setCardBackgroundColor(Color.parseColor("#00E5FF"))
                binding.powerIcon.setColorFilter(Color.parseColor("#00E5FF"))
                binding.outerRing.progress = 0


                val animator = ValueAnimator.ofInt(0, 100)
                animator.duration = 4000
                animator.addUpdateListener { binding.outerRing.progress = it.animatedValue as Int }
                animator.start()

                // Start service
                CameraCaptureService.start(requireContext())


                val checkForeground = object : Runnable {
                    override fun run() {
                        val service = CameraCaptureService.Instance
                        if (service != null && service.isCameraReady) {
                            animator.cancel()
                            binding.outerRing.progress = 100
                        } else {
                            handler.postDelayed(this, 50)
                        }
                    }
                }
                handler.post(checkForeground)

            } else {

                isOn = false
                btnPrefs.enableButton(false)


                binding.powerButton.setCardBackgroundColor(Color.WHITE)
                binding.powerIcon.setColorFilter(Color.WHITE)
                binding.outerRing.progress = 0

                requireContext().stopService(
                    Intent(requireContext(), CameraCaptureService::class.java)
                )



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
                .setDescription(getString(R.string.add_widget))
                .onClick {
                    AddWidget.addWidget(requireContext())
                }.show()
        }
    }

    private fun observeTimer() {
        jobTimer = viewLifecycleOwner.lifecycleScope.launch {
            timerViewModel.secondLeft.collect { seconds ->
                val min = seconds / 60
                val sec = seconds % 60
                binding.tvTimer.text = String.format("%02d:%02d", min, sec)
            }
        }
    }
}
