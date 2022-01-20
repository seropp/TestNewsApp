package com.example.testnewsapp.headlines_categories

interface DataForApiRequest {

    private fun getCountry(): String {
        return "ru"

    }

    private fun getLanguage(): String {
        val languagesMap = mapOf(
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
        return languagesMap["Arabic"].toString()
    }
    private fun getSource(): String {
        return "CNN"
    }
}