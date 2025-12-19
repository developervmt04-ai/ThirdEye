package com.example.thirdeye.ui.paywal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.example.thirdeye.R
import com.example.thirdeye.data.localData.PlansData
import com.example.thirdeye.databinding.FragmentPayWallBinding
import com.example.thirdeye.databinding.PaywallItemBinding
import org.w3c.dom.Text

class PayWallFragment : Fragment() {
    private lateinit var binding: FragmentPayWallBinding
    private lateinit var plans: List<PlansData>

    var selectedPlan: PlansData?=null

    private var selectedRadioButton: RadioButton?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPayWallBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plans=listOf(
            PlansData("1 Weak","RS 650","Rs 100 per day" ),
            PlansData("1 Month","RS 2000","Rs 1000 per day" ),
            PlansData("3 Month","RS 4650","Rs 100 per day" ),

        )
        populatePlans()



    }

    private fun populatePlans() {
        binding.rgPlans.removeAllViews()
        plans.forEach { plan->
            val row= layoutInflater.inflate(R.layout.paywall_item,binding.rgPlans,false)

            val radioBtn=row.findViewById<RadioButton>(R.id.rbPlans)
            val duration=row.findViewById<TextView>(R.id.tvDuration)
            val description=row.findViewById<TextView>(R.id.tvDescription)
            val price= row.findViewById<TextView>(R.id.price)

            radioBtn.id=View.generateViewId()
            duration.text= plan.duration
            description.text=plan.description
            price.text=plan.price

            row.setOnClickListener {
                handleButton(radioBtn,plan)
            }
            radioBtn.setOnClickListener {
                handleButton(radioBtn,plan)
            }

            binding.rgPlans.addView(row)

        }

    }
    fun handleButton(rb: RadioButton,plan: PlansData){

        selectedRadioButton?.isChecked=false
        rb.isChecked=true
        selectedPlan= plan
        selectedRadioButton=rb






    }


}