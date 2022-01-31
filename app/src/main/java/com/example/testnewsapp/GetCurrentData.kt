package com.example.testnewsapp

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.testnewsapp.api.RequestManagerForNewsAPI
import com.example.testnewsapp.api.RetrofitInstance
import com.example.testnewsapp.models.Source
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class GetCurrentData {

    fun getAllSources(context: Context, sourcesList: ArrayList<Source>) {

        val language = getCurrentLanguage(context)
        val country = getCurrentRegion(context)


        val manager = RequestManagerForNewsAPI(context)
        manager.findAllSources(
            list = sourcesList,
            language = language,
            country = country
        )


        val list: ArrayList<String> = arrayListOf()
        sourcesList.forEach {
            list.add(it.name!!)
        }
        chooseSources(list, context)
    }

    private fun chooseSources(list: ArrayList<String>, context: Context) {


        val sources = list.toTypedArray()
        val selectedItemList = arrayListOf<String>()

        MaterialAlertDialogBuilder(context)
            .setTitle("Sources")
            .setMultiChoiceItems(
                sources, null
            ) { _, p1, p2 ->
                if (p2) {
                    selectedItemList.add(sources[p1])
                } else selectedItemList.remove(sources[p1])
            }
            .setPositiveButton("Ok") { _, _ ->
                val pref: SharedPreferences =
                    context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.apply {
                    putString("SOURCES", TextUtils.join(",", selectedItemList))
                }.apply()
            }
            .setNegativeButton("Cancel", null)
            .show()

    }

    fun chooseCountry(context: Context) {

        var country = 0
        val list = countriesMap.keys.toList().sorted().toTypedArray()
        MaterialAlertDialogBuilder(context)
            .setTitle("Countries")
            .setSingleChoiceItems(list, country) { _, which ->
                country = which
            }
            .setPositiveButton("Ok") { _, _ ->

                val pref: SharedPreferences =
                    context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.apply {
                    putString("COUNTRY", countriesMap[list[country]])
                }.apply()

                Toast.makeText(
                    context,
                    "Current region: " + list[country],
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun getCurrentLanguage(context: Context): String? {
        val pref: SharedPreferences =
            context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        return pref.getString("LANGUAGE", null)
    }

    fun getCurrentRegion(context: Context): String? {
        val pref: SharedPreferences =
            context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        return pref.getString("COUNTRY", null)
    }

    fun getTotalTime(context: Context): Long {
        val pref: SharedPreferences =
            context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        return pref.getLong("TIME", 0)
    }


    fun getCurrentSources(context: Context): String? {
        val pref: SharedPreferences =
            context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
        return pref.getString("SOURCES", null)
    }

    fun chooseLanguage(context: Context) {
        var language = 0
        val list = languagesMap.keys.toList().sorted().toTypedArray()
        MaterialAlertDialogBuilder(context)
            .setTitle("Languages")
            .setSingleChoiceItems(list, language) { _, which ->
                language = which
            }
            .setPositiveButton("Ok") { _, _ ->

                val pref: SharedPreferences =
                    context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.apply {
                    putString("LANGUAGE", languagesMap[list[language]])
                }.apply()

                Toast.makeText(
                    context,
                    "Current language: " + list[language],
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private val countriesMap = mapOf(
        "Any" to null,
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
        "Any" to null,
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