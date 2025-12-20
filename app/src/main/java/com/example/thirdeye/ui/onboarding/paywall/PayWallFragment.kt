package com.example.thirdeye.ui.onboarding.paywall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.thirdeye.R
import com.example.thirdeye.data.localData.PlansData
import com.example.thirdeye.databinding.FragmentPayWallBinding
import kotlinx.coroutines.launch

class PayWallFragment : Fragment() {
    private lateinit var binding: FragmentPayWallBinding
    private var plans: List<PlansData> = emptyList()


    var selectedPlan: PlansData? = null

    private var selectedRadioButton: RadioButton? = null
    private val planViewModel: PlanViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayWallBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planViewModel.loadPlans()


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                androidx.lifecycle.Lifecycle.State.STARTED
            ) {
                planViewModel.plans.collect { planList ->
                    plans = planList
                    populatePlans()
                }
            }
        }


    }

    private fun populatePlans() {
        binding.rgPlans.removeAllViews()
        plans.forEach { plan ->
            val row = layoutInflater.inflate(R.layout.paywall_item, binding.rgPlans, false)

            val radioBtn = row.findViewById<RadioButton>(R.id.rbPlans)
            val duration = row.findViewById<TextView>(R.id.tvDuration)
            val description = row.findViewById<TextView>(R.id.tvDescription)
            val price = row.findViewById<TextView>(R.id.price)

            radioBtn.id = View.generateViewId()
            duration.text = plan.duration
            description.text = plan.description
            price.text = plan.price

            row.setOnClickListener {
                handleButton(radioBtn, plan)
            }
            radioBtn.setOnClickListener {
                handleButton(radioBtn, plan)
            }

            binding.rgPlans.addView(row)

        }

    }

    fun handleButton(rb: RadioButton, plan: PlansData) {

        selectedRadioButton?.isChecked = false
        rb.isChecked = true
        selectedPlan = plan
        selectedRadioButton = rb


    }


}