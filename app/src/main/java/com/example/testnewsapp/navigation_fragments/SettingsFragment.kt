package com.example.testnewsapp.navigation_fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.testnewsapp.GetCurrentData
import com.example.testnewsapp.R
import com.example.testnewsapp.internet_connection.InternetConnection
import com.example.testnewsapp.internet_connection.NetworkManager
import com.example.testnewsapp.models.Source
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class SettingsFragment : Fragment() {


    private lateinit var regionText: TextView
    private lateinit var languageText: TextView
    private lateinit var regionChangeBtn: Button
    private lateinit var languageChangeBtn: Button
    private lateinit var sourcesChangeBtn: Button
    private lateinit var setPreferences: Button

    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

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
        setPreferences = view.findViewById(R.id.set_preferences_btn)

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
            GetCurrentData().getAllSources(requireContext(), list)
        }

        setPreferences.setOnClickListener {
            if (user != null) {
                if (NetworkManager.isNetworkAvailable(requireContext())) {
                    setDefaultData()
                } else {
                    requireContext().startActivity(
                        Intent(
                            requireContext(),
                            InternetConnection::class.java
                        )
                    )
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "This function is available only to authorized users",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun changeRegionTxt() {
        val pref: SharedPreferences =
            requireActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val rg = pref.getString("COUNTRY", null)
        if (rg == null) {
            regionText.text = "All regions"
        } else {
            regionText.text = "Current region: $rg"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changeLanguageTxt() {
        val pref: SharedPreferences =
            requireActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val lg = pref.getString("LANGUAGE", null)
        if (lg == null) {
            languageText.text = "All languages"
        } else {
            languageText.text = "Current language: $lg"
        }
    }


    private fun setDefaultData() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            val users: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
            val currentUserReference = users.child(FirebaseAuth.getInstance().currentUser!!.uid)
            currentUserReference
                .child("info")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        currentUserReference.child("default_region")
                            .setValue(GetCurrentData().getCurrentRegion(requireContext()))
                        currentUserReference.child("default_language")
                            .setValue(GetCurrentData().getCurrentLanguage(requireContext()))
                        currentUserReference.child("default_sources")
                            .setValue(GetCurrentData().getCurrentSources(requireContext()))
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }

}



