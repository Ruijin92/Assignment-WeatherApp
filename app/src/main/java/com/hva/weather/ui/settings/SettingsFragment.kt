package com.hva.weather.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.hva.weather.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}