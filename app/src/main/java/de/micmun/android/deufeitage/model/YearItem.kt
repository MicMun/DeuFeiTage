/*
 * YearItem.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.model

/**
 * Item for year selection.
 *
 * @author MicMun
 * @version 1.0, 07.08.18
 */
class YearItem(val year: Int) {
    var isSelected: Boolean = false

    override fun toString(): String {
        return year.toString()
    }

    override fun equals(other: Any?): Boolean {
        var isEqual = true

        if (other == null)
            isEqual = false
        else if (other !is YearItem)
            isEqual = false
        else {
            val yearItem: YearItem = other
            if (year != yearItem.year)
                isEqual = false
        }

        return isEqual
    }

    override fun hashCode(): Int {
        return year * 13
    }
}