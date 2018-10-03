/*
 * StateItem.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.model

/**
 * Item for state selection.
 *
 * @author MicMun
 * @version 1.0, 10.07.18
 */
class StateItem(val name: String, val desc: String, val holidays: List<Holiday>) {
    override fun toString() = name
}