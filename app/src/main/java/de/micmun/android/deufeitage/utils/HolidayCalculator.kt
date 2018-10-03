/*
 * HolidayCalculator.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.utils

import android.content.Context
import de.micmun.android.deufeitage.R
import de.micmun.android.deufeitage.model.Holiday
import java.util.*

/**
 * Calculates the holidays.
 *
 * @author MicMun
 * @version 1.0, 07.08.18
 */
class HolidayCalculator(private val context: Context) {
    private val holidays = mutableListOf<Holiday>()

    /**
     * Returns a list of holidays.
     *
     * @return list of holidays.
     */
    fun getHolidays(year: Int): List<Holiday> {
        calculate(year)
        return holidays
    }

    /**
     * Calculates the holidays.
     */
    private fun calculate(year: Int) {
        holidays.clear()
        val names = context.resources.getStringArray(R.array.names_of_holidays)

        val easterSunday = getEasterSunday(year)

        for (i in 0 until names.size) {
            val name = names[i]
            // Date of holiday
            val date: Calendar = when (i) {
                0 -> GregorianCalendar(year, Calendar.JANUARY, 1, 0, 0, 0)
                1 -> GregorianCalendar(year, Calendar.JANUARY, 6, 0, 0, 0)
                2 -> {
                    val karf = GregorianCalendar(year, easterSunday[Calendar.MONTH],
                            easterSunday[Calendar.DAY_OF_MONTH], 0, 0, 0)
                    karf.add(Calendar.DAY_OF_MONTH, -2)
                    karf
                }
                3 -> easterSunday
                4 -> {
                    val easterMonday = GregorianCalendar(year, easterSunday[Calendar.MONTH],
                            easterSunday[Calendar.DAY_OF_MONTH], 0, 0, 0)
                    easterMonday.add(Calendar.DAY_OF_MONTH, 1)
                    easterMonday
                }
                5 -> GregorianCalendar(year, Calendar.MAY, 1, 0, 0, 0)
                6 -> {
                    val christi = GregorianCalendar(year, easterSunday[Calendar.MONTH],
                            easterSunday[Calendar.DAY_OF_MONTH], 0, 0, 0)
                    christi.add(Calendar.DAY_OF_MONTH, 39)
                    christi
                }
                7 -> {
                    val whitsunday = GregorianCalendar(year, easterSunday[Calendar.MONTH],
                            easterSunday[Calendar.DAY_OF_MONTH], 0, 0, 0)
                    whitsunday.add(Calendar.DAY_OF_MONTH, 49)
                    whitsunday
                }
                8 -> {
                    val whitmonday = GregorianCalendar(year, easterSunday[Calendar.MONTH],
                            easterSunday[Calendar.DAY_OF_MONTH], 0, 0, 0)
                    whitmonday.add(Calendar.DAY_OF_MONTH, 50)
                    whitmonday
                }
                9 -> {
                    val fron = GregorianCalendar(year, easterSunday[Calendar.MONTH],
                            easterSunday[Calendar.DAY_OF_MONTH], 0, 0, 0)
                    fron.add(Calendar.DAY_OF_MONTH, 60)
                    fron
                }
                10 -> GregorianCalendar(year, Calendar.AUGUST, 15, 0, 0, 0)
                11 -> GregorianCalendar(year, Calendar.OCTOBER, 3, 0, 0, 0)
                12 -> GregorianCalendar(year, Calendar.OCTOBER, 31, 0, 0, 0)
                13 -> GregorianCalendar(year, Calendar.NOVEMBER, 1, 0, 0, 0)
                14 -> getBubetday(year, easterSunday)
                15 -> GregorianCalendar(year, Calendar.DECEMBER, 25, 0, 0, 0)
                16 -> GregorianCalendar(year, Calendar.DECEMBER, 26, 0, 0, 0)

                else -> {
                    val gc = GregorianCalendar.getInstance()
                    gc[Calendar.HOUR] = 0
                    gc[Calendar.MINUTE] = 0
                    gc[Calendar.SECOND] = 0
                    gc
                }
            }

            // describtion of holiday
            val desc: String = when (i) {
                0 -> "Test"
                3 -> "Easter sunday is the day of jesus christ reborn"
                else -> "Test"
            }
            holidays.add(Holiday(name, date, desc))
        }
    }

    /**
     * Returns the easter sunday from this year.
     *
     * @param year year for easter sunday calculation.
     * @return calendar with date of easter sunday.
     */
    private fun getEasterSunday(year: Int): Calendar {
        val a = year % 19
        val b = year / 100
        val c = year % 100
        val d = b / 4
        val e = b % 4
        val f = (b + 8) / 25
        val g = (b - f + 1) / 3
        val h = (19 * a + b - d - g + 15) % 30
        val i = c / 4
        val k = c % 4
        val l = (32 + 2 * e + 2 * i - h - k) % 7
        val m = (a + 11 * h + 22 * l) / 451
        val n = h + l - 7 * m + 114
        val month = n / 31 - 1  // months indexed from 0
        val day = (n % 31) + 1

        return GregorianCalendar(year, month, day, 0, 0, 0)
    }

    /**
     * Returns the bubetday of the given year.
     *
     * @param year year for bubetday calculation.
     * @param easter date of easter sunday.
     * @return calendar with date of bubetday.
     */
    private fun getBubetday(year: Int, easter: Calendar): Calendar {
        val bubetday = GregorianCalendar.getInstance()
        val number: Int = if (easter[Calendar.MONTH] == Calendar.MARCH) 33 else 30
        val dayNr: Int = (-1) * ((number - easter[Calendar.DAY_OF_MONTH]) % 7)

        bubetday.clear()
        bubetday.set(year, Calendar.NOVEMBER, 22, 0, 0, 0)
        bubetday.add(Calendar.DAY_OF_MONTH, dayNr)

        return bubetday
    }
}