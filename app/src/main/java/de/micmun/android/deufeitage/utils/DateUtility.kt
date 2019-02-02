/*
 * DateUtility.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Methods for date and time.
 *
 * @author MicMun
 * @version 1.0, 07.08.18
 */
class DateUtility {
    companion object {
        /**
         * Returns a formatted date string.
         *
         * @param date Date to format.
         * @return formatted date string.
         */
        fun getFormattedDate(date: Calendar): String {
            var df = SimpleDateFormat("EE MM/dd", Locale.getDefault())

            if (Locale.getDefault() == Locale.GERMANY || Locale.getDefault() == Locale("de_AT") ||
                    Locale.getDefault() == Locale("de_CH")) {
                df = SimpleDateFormat("EE dd.MM.", Locale.getDefault())
            }

            return df.format(date.time)
        }
    }
}
