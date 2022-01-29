package com.example.testnewsapp.onboarding.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.example.testnewsapp.R



class FirstFragment : Fragment() {

    private lateinit var btnNext: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_first, container, false)

        val viewPager = activity?.findViewById<ViewPager>(R.id.onboarding_view_pager)

        btnNext = view.findViewById(R.id.next_first_screen)
        btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }


        return view
    }

}