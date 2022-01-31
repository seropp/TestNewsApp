package com.example.testnewsapp.onboarding.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.example.testnewsapp.internet_connection.InternetConnection
import com.example.testnewsapp.internet_connection.NetworkManager
import com.example.testnewsapp.models.Source
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


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
        val view = inflater.inflate(R.layout.fragment_third, container, false)

        list = arrayListOf()

        btnFinish = view.findViewById(R.id.finish_third_screen)
        btnFinish.setOnClickListener {

            if (NetworkManager.isNetworkAvailable(requireContext())) {

                setDefaultData()
                findNavController().navigate(R.id.action_onboardingViewPagerFragment2_to_mainActivity)

            } else {
                requireContext().startActivity(
                    Intent(
                        requireContext(),
                        InternetConnection::class.java
                    )
                )
            }

        }



        currentLanguageTxt = view.findViewById(R.id.third_screen_language)
        btnSelectLanguage = view.findViewById(R.id.choose_language_third_screen)
        btnSelectLanguage.setOnClickListener {
            GetCurrentData().chooseLanguage(requireContext())
            setTxt()
        }

        btnSelectSources = view.findViewById(R.id.choose_sources_third_screen)
        btnSelectSources.setOnClickListener {
            GetCurrentData().getAllSources(requireContext(), list)
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setTxt() {
        val rl = GetCurrentData().getCurrentLanguage(requireContext())

        if (rl == null) currentLanguageTxt.text = "All language"
        else currentLanguageTxt.text = "Current language: $rl"
    }

    private fun setDefaultData() {

        if (FirebaseAuth.getInstance().currentUser != null) {

            val path: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("info")

            path.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    path.child("onboarding").setValue("yes")
                    path.child("default_region")
                        .setValue(GetCurrentData().getCurrentRegion(requireContext()))
                    path.child("default_language")
                        .setValue(GetCurrentData().getCurrentLanguage(requireContext()))
                    path.child("default_sources")
                        .setValue(GetCurrentData().getCurrentSources(requireContext()))
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

}