/*
 * StateAdapter.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import de.micmun.android.deufeitage.StateFragment

/**
 * Adapter for the country states.
 *
 * @author MicMun
 * @version 1.0, 18.02.18
 */
class StateAdapter(private val fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> StateFragment.newInstance("Baden-Württemberg")
        1 -> StateFragment.newInstance("Bayern")
        2 -> StateFragment.newInstance("Bremen")
        else -> null
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> "Baden-Württemberg"
        1 -> "Bayern"
        2 -> "Bremen"
        else -> ""
    }

    override fun getCount(): Int = 3
}