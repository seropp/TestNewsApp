package com.example.testnewsapp.navigation_fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.testnewsapp.GetCurrentData

import com.example.testnewsapp.R
import com.example.testnewsapp.models.Source



class SettingsFragment : Fragment() {


    private lateinit var regionText: TextView
    private lateinit var languageText: TextView
    private lateinit var regionChangeBtn: Button
    private lateinit var languageChangeBtn: Button
    private lateinit var sourcesChangeBtn: Button

    private lateinit var list: ArrayList<Source>




    @SuppressLint("InflateParams")
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.settings_fragment, null)

        list = arrayListOf()

        regionText = view.findViewById(R.id.current_region)
        languageText = view.findViewById(R.id.current_language)

        regionChangeBtn = view.findViewById(R.id.current_region_btn)
        languageChangeBtn = view.findViewById(R.id.current_language_btn)
        sourcesChangeBtn = view.findViewById(R.id.current_sources_btn)

        changeRegionTxt()
        changeLanguageTxt()

        regionChangeBtn.setOnClickListener {
            GetCurrentData().chooseCountry(requireContext())
            changeRegionTxt()
        }
        languageChangeBtn.setOnClickListener {
            GetCurrentData().chooseLanguage(requireContext())
            changeLanguageTxt()
        }

        sourcesChangeBtn.setOnClickListener {
            GetCurrentData().getAllSources(requireContext(),list)
        }

        return view
    }




    @SuppressLint("SetTextI18n")
    private fun changeRegionTxt() {
        val pref: SharedPreferences =
            requireActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val rg = pref.getString("COUNTRY", "")
        if(rg == "") {
            regionText.text = "All regions"
        }
        else {
            regionText.text = "Current region: $rg"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changeLanguageTxt() {
        val pref: SharedPreferences =
            requireActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val lg = pref.getString("LANGUAGE", null)
        if (lg == "" ) {
            languageText.text = "All languages"
        } else {
            languageText.text = "Current language: $lg"
        }
    }






}



