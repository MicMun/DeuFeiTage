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
data class StateItem(val key: String, val name: String) {
    override fun toString() = name
}