package com.example.testnewsapp.navigation_fragments

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

import com.example.testnewsapp.R
import com.example.testnewsapp.login.LoginActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {


    private lateinit var regionText: TextView
    private lateinit var languageText: TextView
    private lateinit var regionChangeBtn: Button
    private lateinit var languageChangeBtn: Button
    private lateinit var sourcesChangeBtn: Button

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.settings_fragment, null)



        regionText = view.findViewById(R.id.current_region)
        languageText = view.findViewById(R.id.current_language)

        regionChangeBtn = view.findViewById(R.id.current_region_btn)
        languageChangeBtn = view.findViewById(R.id.current_language_btn)
        sourcesChangeBtn = view.findViewById(R.id.current_sources_btn)

        changeRegionTxt()
        changeLanguageTxt()



        regionChangeBtn.setOnClickListener {
            chooseCountry()
        }


        languageChangeBtn.setOnClickListener {
            chooseLanguage()
        }


        return view
    }


    private fun chooseCountry() {

        var country = 0
        val list = countriesMap.keys.toList().sorted().toTypedArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Countries")
            .setSingleChoiceItems(list, country) { dialog, which ->
                country = which
            }
            .setPositiveButton("Ok") { dialog, which ->


                val pref: SharedPreferences =
                    requireContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.apply {
                    putString("COUNTRY", countriesMap[list[country]])
                }.apply()
                changeRegionTxt()
                Toast.makeText(
                    requireContext(),
                    "Current region: " + list[country],
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun chooseLanguage() {
        var language = 0
        val list = languagesMap.keys.toList().sorted().toTypedArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Languages")
            .setSingleChoiceItems(list, language) { dialog, which ->
                language = which
            }
            .setPositiveButton("Ok") { dialog, which ->


                val pref: SharedPreferences =
                    requireContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.apply {
                    putString("LANGUAGE", languagesMap[list[language]])
                }.apply()
                changeLanguageTxt()

                Toast.makeText(
                    requireContext(),
                    "Current language: " + list[language],
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun changeRegionTxt() {
        val pref: SharedPreferences =
            requireContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val rg = pref.getString("COUNTRY", "null")!!
        regionText.text = "Current region: $rg"
    }

    private fun changeLanguageTxt() {
        val pref: SharedPreferences =
            requireContext().getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        val lg = pref.getString("LANGUAGE", "null")!!
        languageText.text = "Current language: $lg"
    }

    private val countriesMap = mapOf(
        "Argentina" to "ar",
        "Australia" to "au",
        "Austria" to "at",
        "Belgium" to "be",
        "Brazil" to "br",
        "Bulgaria" to "bg",
        "Canada" to "ca",
        "China" to "cn",
        "Colombia" to "co",
        "Cuba" to "cu",
        "Czechia" to "cz",
        "Egypt" to "eg",
        "France" to "fr",
        "Germany" to "de",
        "Greece" to "gr",
        "Honk Kong" to "hk",
        "Hungary" to "hu",
        "Indonesia" to "id",
        "Ireland" to "ie",
        "Israel" to "il",
        "India" to "in",
        "Italy" to "it",
        "Japan" to "jp",
        "Korea" to "kr",
        "Latvia" to "lv",
        "Lithuania" to "lt",
        "Morocco" to "ma",
        "Mexico" to "mx",
        "Malaysia" to "my",
        "Nigeria" to "ng",
        "Netherlands" to "nl",
        "Norway" to "no",
        "New Zealand" to "nz",
        "Philippines" to "ph",
        "Poland" to "pl",
        "Portugal" to "pt",
        "Romania" to "ro",
        "Russia" to "ru",
        "Serbia" to "rs",
        "Saudi Arabia" to "sa",
        "Sweden" to "se",
        "Singapore" to "sg",
        "Slovenia" to "si",
        "Slovakia" to "sk",
        "South Africa" to "za",
        "Switzerland" to "ch",
        "Thailand" to "th",
        "Turkey" to "tr",
        "Taiwan" to "tw",
        "Ukraine" to "ua",
        "United Arab Emirates" to "ae",
        "United Kingdom of Great Britain and Northern Ireland" to "gb",
        "USA" to "us",
        "Venezuela" to "ve"
    )
    private val languagesMap = mapOf(
        "Arabic" to "ar",
        "Chinese" to "zh",
        "Dutch" to "nl",
        "English" to "en",
        "French" to "fr",
        "German" to "de",
        "Hebrew" to "he",
        "Italian" to "it",
        "Norwegian" to "no",
        "Portuguese" to "pt",
        "Russian" to "ru",
        "Sami" to "se",
        "Spanish" to "es",
    )
}

