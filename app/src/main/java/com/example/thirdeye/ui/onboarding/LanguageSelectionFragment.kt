package com.example.thirdeye.ui.onboarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.example.thirdeye.R
import com.example.thirdeye.data.localData.SecurityPrefs
import com.example.thirdeye.data.models.LanguageData
import com.example.thirdeye.databinding.FragmentLanguageSelectionBinding

class LanguageSelectionFragment : Fragment() {
    private lateinit var binding: FragmentLanguageSelectionBinding
    private lateinit var languages:List<LanguageData>
    private var selectedLanguage: LanguageData?=null
    private var selectedRadioButton: RadioButton? = null
    private lateinit var securityPrefs: SecurityPrefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLanguageSelectionBinding.inflate(layoutInflater,container,false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        securityPrefs= SecurityPrefs(requireContext())

        languages=listOf(
            LanguageData("English",R.drawable.englishicon),
            LanguageData("Hindi",R.drawable.hindiicon),
            LanguageData("Indoenesian",R.drawable.indonesionicon),
            LanguageData("Arabic",R.drawable.arabicicon),
            LanguageData("Spanish",R.drawable.spanishicon),
            LanguageData("Portagues",R.drawable.portaguesicon),
            LanguageData("Korean",R.drawable.koreanicon),


        )
      populateLanguages()



    }
    private fun populateLanguages() {
        binding.rgLanguages.removeAllViews()

        languages.forEach { languageData ->

            val row = layoutInflater.inflate(
                R.layout.language_item,
                binding.rgLanguages,
                false
            )

            val radioButton = row.findViewById<RadioButton>(R.id.rbLanguage)
            val textView = row.findViewById<TextView>(R.id.tvLanguageName)
            val imageView = row.findViewById<ImageView>(R.id.ivLanguageIcon)

            radioButton.id = View.generateViewId()

            textView.text = languageData.language
            imageView.setImageResource(languageData.img)


            row.setOnClickListener {
                handleSelection(radioButton, languageData)
            }
            radioButton.setOnClickListener {
                handleSelection(radioButton, languageData)
            }

            binding.rgLanguages.addView(row)
        }
    }

    private fun handleSelection(radioButton: RadioButton, languageData: LanguageData) {

        selectedRadioButton?.isChecked = false

        // Check the new one
        radioButton.isChecked = true

        selectedLanguage = languageData
        Log.d("Language","$selectedLanguage")
        selectedRadioButton = radioButton
        securityPrefs.selectedLanguage=languageData.language
    }





}
