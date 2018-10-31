/*
 * HolidayAdapter.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.adapter

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import de.micmun.android.deufeitage.R
import de.micmun.android.deufeitage.model.Holiday
import de.micmun.android.deufeitage.utils.DateUtility

/**
 * Adapter for holiday recycler view.
 *
 * @author MicMun
 * @version 1.0, 07.08.18
 */
class HolidayAdapter(var holidays: List<Holiday>) :
        RecyclerView.Adapter<HolidayAdapter.HolidayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.holiday_row, parent, false) as ConstraintLayout
        return HolidayViewHolder(linearLayout)
    }

    override fun getItemCount(): Int = holidays.size + 1

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        if (position == 0) {
            holder.holidayDate.text = "Datum"
            holder.holidayDate.setTypeface(null, Typeface.BOLD)
            holder.holidayText.text = "Feiertag"
            holder.holidayText.setTypeface(null, Typeface.BOLD)
            holder.holidayText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            holder.holidayDiff.text = "Differenz"
            holder.holidayDiff.setTypeface(null, Typeface.BOLD)
        } else {
            val holiday = holidays[position - 1]
            holder.holidayText.text = holiday.name
            holder.holidayText.setTypeface(null, Typeface.NORMAL)
            holder.holidayText.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
            holder.holidayDate.text = DateUtility.getFormattedDate(holiday.datum)
            holder.holidayDate.setTypeface(null, Typeface.NORMAL)
            holder.holidayDiff.text = "${holiday.getDiffToNow()} Tage"
            holder.holidayDiff.setTypeface(null, Typeface.NORMAL)
            holder.holidayDesc.text = holiday.desc

            holder.holidayLayout.setOnClickListener {
                if (holder.descLayout.visibility == LinearLayout.GONE)
                    holder.descLayout.visibility = LinearLayout.VISIBLE
                else
                    holder.descLayout.visibility = LinearLayout.GONE
            }

            Log.d("HolidayAdapter", "Position = $position, Holiday = ${holiday.name}")
        }
    }

    class HolidayViewHolder(val view: ConstraintLayout) : RecyclerView.ViewHolder(view) {
        val holidayText: TextView = view.findViewById(R.id.holidayText)
        val holidayDate: TextView = view.findViewById(R.id.holidayDate)
        val holidayDiff: TextView = view.findViewById(R.id.holidayDiff)
        val holidayDesc: TextView = view.findViewById(R.id.holidayDesc)

        val holidayLayout: LinearLayout = view.findViewById(R.id.holidayLayout)
        val descLayout: LinearLayout = view.findViewById(R.id.descLayout)
    }
}