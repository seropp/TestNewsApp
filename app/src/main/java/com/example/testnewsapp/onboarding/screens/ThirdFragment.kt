package com.example.testnewsapp.onboarding.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.testnewsapp.GetCurrentData
import com.example.testnewsapp.R
import com.example.testnewsapp.models.Source


class ThirdFragment : Fragment() {

    private lateinit var btnFinish: Button
    private lateinit var btnSelectLanguage: Button
    private lateinit var btnSelectSources: Button
    private lateinit var currentLanguageTxt: TextView
    private lateinit var list: ArrayList<Source>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_third, container, false)

        list = arrayListOf()

        val viewPager = activity?.findViewById<ViewPager>(R.id.onboarding_view_pager)
        btnFinish = view.findViewById(R.id.finish_third_screen)
        btnFinish.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingViewPagerFragment2_to_mainActivity)
        }

        currentLanguageTxt = view.findViewById(R.id.third_screen_language)
        btnSelectLanguage = view.findViewById(R.id.choose_language_third_screen)
        btnSelectLanguage.setOnClickListener {
            GetCurrentData().chooseLanguage(requireContext())

            val pref: SharedPreferences =
                requireActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
            val rl = pref.getString("LANGUAGE", null)

            if(rl == null) currentLanguageTxt.text = "All regions"
            else currentLanguageTxt.text = "Current region: $rl"
        }

        btnSelectSources = view.findViewById(R.id.choose_sources_third_screen)
        btnSelectSources.setOnClickListener {
            GetCurrentData().getAllSources(requireContext(),list)
        }



        return view
    }

}