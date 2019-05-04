/*
 * HolidayConfigReader.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.utils

import android.content.Context
import de.micmun.android.deufeitage.R
import de.micmun.android.deufeitage.model.Holiday
import de.micmun.android.deufeitage.model.StateItem
import org.json.JSONArray
import org.json.JSONObject

/**
 * Reads the config from json file.
 *
 * @author MicMun
 * @version 1.1, 04.05.19
 */
class HolidayConfigReader(context: Context) {
    private val keyStates = "States"
    private val keyHolidays = "Holidays"
    private val keyKey = "KEY"
    private val keyValue = "VALUE"
    private val keyId = "ID"
    private val keyName = "Name"
    private val keyDesc = "Description"

    val holidays = mutableListOf<Holiday>()
    val states = mutableListOf<StateItem>()

    init {
        val json = context.resources.openRawResource(R.raw.holidays).bufferedReader()
                .use { it.readText() }
        val jsonObject = JSONObject(json)
        val statesArray = jsonObject[keyStates] as JSONArray

        for (i in 0 until statesArray.length()) {
            val stateObj = statesArray[i] as JSONObject
            val state = StateItem(stateObj[keyKey].toString(), stateObj[keyValue].toString())
            states.add(state)
        }

        val holidayArray: JSONArray = jsonObject[keyHolidays] as JSONArray

        for (i in 0 until holidayArray.length()) {
            val holidayObj = holidayArray[i] as JSONObject
            val holidayStateArr = holidayObj[keyStates] as JSONArray
            val holidayStates = mutableListOf<String>()

            for (j in 0 until holidayStateArr.length()) {
                holidayStates.add(holidayStateArr[j].toString())
            }

            val holiday = Holiday(holidayObj[keyId] as Int, holidayObj[keyName] as String,
                    holidayStates, holidayObj[keyDesc] as String)
            holidays.add(holiday)
        }
    }
}