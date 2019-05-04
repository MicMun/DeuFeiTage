/*
 * HolidayAdapter.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.adapter

import android.content.Context
import android.graphics.Typeface
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
 * @version 1.1, 04.05.19
 */
class HolidayAdapter(private val context: Context, var holidays: List<Holiday>) :
        RecyclerView.Adapter<HolidayAdapter.HolidayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.holiday_row, parent, false) as ConstraintLayout
        return HolidayViewHolder(linearLayout)
    }

    override fun getItemCount(): Int = holidays.size + 1

    /**
     * @see RecyclerView.Adapter#onBindViewHolder
     */
    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        if (position == 0) {
            holder.holidayDate.text = context.getString(R.string.title_date)
            holder.holidayDate.setTextAppearance(R.style.TextAppearance_AppCompat_Body2)
            holder.holidayText.text = context.getString(R.string.title_holiday)
            holder.holidayText.setTextAppearance(R.style.TextAppearance_AppCompat_Body2)
            holder.holidayText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            holder.holidayDiff.text = context.getString(R.string.title_difference)
            holder.holidayDiff.setTextAppearance(R.style.TextAppearance_AppCompat_Body2)

        } else {
            val nextHoliday = DateUtility.getNextHoliday(holidays)
            val holiday = holidays[position - 1]

            holder.holidayText.text = holiday.name
            holder.holidayText.setTypeface(null, Typeface.NORMAL)
            holder.holidayText.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
            holder.holidayDate.text = DateUtility.getFormattedDate(holiday.datum)
            holder.holidayDate.setTypeface(null, Typeface.NORMAL)
            holder.holidayDiff.text = context.getString(R.string.diff_text, holiday.getDiffToNow())
            holder.holidayDiff.setTypeface(null, Typeface.NORMAL)
            holder.holidayDesc.text = holiday.desc

            holder.holidayLayout.setOnClickListener {
                if (holder.descLayout.visibility == LinearLayout.GONE)
                    holder.descLayout.visibility = LinearLayout.VISIBLE
                else
                    holder.descLayout.visibility = LinearLayout.GONE
            }

            if (nextHoliday == holiday) {
                holder.view.background = holder.view.context.resources
                        .getDrawable(R.drawable.background_indicator_next, null)
            } else {
                holder.view.background = holder.view.context.resources
                        .getDrawable(R.drawable.background_indicator_normal, null)
            }
        }
    }

    /**
     * HolidayViewHolder for the views in a line.
     *
     * @param view root view of the line.
     * @property holidayText textview of the holiday.
     */
    class HolidayViewHolder(val view: ConstraintLayout) : RecyclerView.ViewHolder(view) {
        val holidayText: TextView = view.findViewById(R.id.holidayText)
        val holidayDate: TextView = view.findViewById(R.id.holidayDate)
        val holidayDiff: TextView = view.findViewById(R.id.holidayDiff)
        val holidayDesc: TextView = view.findViewById(R.id.holidayDesc)

        val holidayLayout: LinearLayout = view.findViewById(R.id.holidayLayout)
        val descLayout: LinearLayout = view.findViewById(R.id.descLayout)
    }
}