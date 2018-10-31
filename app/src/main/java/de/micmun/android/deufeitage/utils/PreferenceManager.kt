/*
 * PreferenceManager.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.utils

import android.content.SharedPreferences

/**
 * Handle for preferences.
 *
 * @author MicMun
 * @version 1.0, 31.10.18
 */
class PreferenceManager(private val preferences: SharedPreferences, private val version: String = "") {
    companion object {
        const val pref_name = "deufeitage.conf"
        const val pref_state = "STATE_ID"
        const val pref_year = "YEAR"
        const val pref_version = "VERSION"
    }

    init {
        val currentVersion = preferences.getString(pref_version, "")

        if (currentVersion!!.isEmpty()) {
            val state = preferences.getString("ID", null)
            val year = preferences.getInt(pref_year, -1)

            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()

            editor.putString(pref_state, state)
            editor.putInt(pref_year, year)
            editor.putString(pref_version, version)

            editor.apply()
        }

        if (currentVersion != version) {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString(pref_version, version)
            editor.apply()
        }
    }

    /**
     * Set the year to given value.
     *
     * @param year year to set.
     */
    fun setYear(year: Int) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt(pref_year, year)
        editor.apply()
    }

    /**
     * Returns the current year.
     *
     * @return current year or -1, if not set.
     */
    fun getYear(): Int {
        return preferences.getInt(pref_year, -1)
    }

    /**
     * Set the state to given value.
     *
     * @param state state to set.
     */
    fun setState(state: String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(pref_state, state)
        editor.apply()
    }

    /**
     * Returns the current state.
     *
     * @return current state or null, if not set.
     */
    fun getState(): String? {
        return preferences.getString(pref_state, null)
    }
}