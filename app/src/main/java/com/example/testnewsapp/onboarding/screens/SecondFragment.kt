package com.example.testnewsapp.onboarding.screens

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.testnewsapp.GetCurrentData
import com.example.testnewsapp.R

class SecondFragment : Fragment() {

    private lateinit var btnNext: Button
    private lateinit var btnSelectRegion: Button
    private lateinit var currentRegionTxt: TextView


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onboarding_view_pager)
        btnNext = view.findViewById(R.id.next_second_screen)
        btnNext.setOnClickListener {
            viewPager?.currentItem = 2
        }

        currentRegionTxt = view.findViewById(R.id.second_screen_country)
        btnSelectRegion = view.findViewById(R.id.choose_country_second_screen)
        btnSelectRegion.setOnClickListener {

            GetCurrentData().chooseCountry(requireContext())
            setTxt()
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setTxt() {
        val rg = GetCurrentData().getCurrentRegion(requireContext())

        if (rg == null) currentRegionTxt.text = "All regions"
        else currentRegionTxt.text = "Current region: $rg"
    }
}