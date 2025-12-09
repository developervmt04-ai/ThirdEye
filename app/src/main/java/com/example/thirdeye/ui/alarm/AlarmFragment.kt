package com.example.thirdeye.ui.alarm

import android.R.style.Animation
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import com.example.thirdeye.R
import com.example.thirdeye.databinding.FragmentAlarmBinding


class AlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAlarmBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





       var   isOn:Boolean  = false

      binding.  powerButton.setOnClickListener {
            isOn = !isOn;

            if (isOn) {

               binding. outerRing.setBackgroundResource(R.drawable.ring_on)
                binding.   powerButton.setCardBackgroundColor(Color.parseColor("#00E5FF"))
                binding.  powerIcon.setColorFilter(Color.parseColor("#00E5FF"))
            } else {

                binding. outerRing.setBackgroundResource(R.drawable.ring_off)
                binding. powerButton.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                binding. powerIcon.setColorFilter(Color.parseColor("#FFFFFF"))
            }
        }





    }

}