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
 * @version 1.2, 09.07.19
 */
class HolidayAdapter(private val context: Context, var holidays: List<Holiday>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val layout = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_row, parent, false) as ConstraintLayout
            return HeaderViewHolder(layout)
        } else if (viewType == TYPE_ITEM) {
            val layout = LayoutInflater.from(parent.context)
                    .inflate(R.layout.holiday_row, parent, false) as ConstraintLayout
            return HolidayViewHolder(layout)
        }

        throw RuntimeException("There is no type that matches the type $viewType, make sure your using types correctly")
    }

    override fun getItemCount(): Int = holidays.size + 1

    /**
     * @see RecyclerView.Adapter#onBindViewHolder
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.headerDate.text = context.getString(R.string.title_date)
            holder.headerDate.setTextAppearance(R.style.TextAppearance_AppCompat_Body2)
            holder.headerText.text = context.getString(R.string.title_holiday)
            holder.headerText.setTextAppearance(R.style.TextAppearance_AppCompat_Body2)
            holder.headerText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            holder.headerDiff.text = context.getString(R.string.title_difference)
            holder.headerDiff.setTextAppearance(R.style.TextAppearance_AppCompat_Body2)
        } else if (holder is HolidayViewHolder) {
            val nextHoliday = DateUtility.getNextHoliday(holidays)
            val holiday = getItem(position)

            holder.holidayText.text = holiday.name
            holder.holidayText.setTypeface(null, Typeface.NORMAL)
            holder.holidayText.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
            holder.holidayDate.text = DateUtility.getFormattedDate(holiday.datum)
            holder.holidayDate.setTypeface(null, Typeface.NORMAL)
            holder.holidayDiff.text = context.getString(R.string.diff_text, holiday.getDiffToNow())
            holder.holidayDiff.setTypeface(null, Typeface.NORMAL)
            holder.holidayDesc.text = holiday.desc
            holder.holidayDesc.setTypeface(null, Typeface.ITALIC)
            holder.holidayStates.text = stateListToString(holiday.states)

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

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TYPE_HEADER else TYPE_ITEM

    }

    private fun isPositionHeader(position: Int) = position == 0

    private fun getItem(position: Int): Holiday {
        return holidays[position - 1]
    }

    /**
     * Returns a string representation (comma separated) of a list of states.
     *
     * @param states list of states.
     * @return string representation of the list (comma separated).
     */
    private fun stateListToString(states: List<String>): String {
        val sb = StringBuilder("(")
        var first = true

        if (states[0] == "DE")
            sb.append(states[0])
        else {
            states.forEach { s ->
                if (first)
                    first = false
                else
                    sb.append(", ")

                sb.append(s)
            }
        }
        sb.append(")")

        return sb.toString()
    }

    /**
     * HolidayViewHolder for the views in a line.
     *
     * @param view root view of the line.
     * @return ViewHolder.
     */
    class HolidayViewHolder(val view: ConstraintLayout) : RecyclerView.ViewHolder(view) {
        val holidayText: TextView = view.findViewById(R.id.holidayText)
        val holidayDate: TextView = view.findViewById(R.id.holidayDate)
        val holidayDiff: TextView = view.findViewById(R.id.holidayDiff)
        val holidayDesc: TextView = view.findViewById(R.id.holidayDesc)
        val holidayStates: TextView = view.findViewById(R.id.holidayStates)

        val holidayLayout: ConstraintLayout = view.findViewById(R.id.holidayLayout)
        val descLayout: LinearLayout = view.findViewById(R.id.descLayout)
    }

    /**
     * HeaderViewHolder for the header text line.
     *
     * @param view root view of the line.
     * @return ViewHolder.
     */
    class HeaderViewHolder(val view: ConstraintLayout) : RecyclerView.ViewHolder(view) {
        val headerText: TextView = view.findViewById(R.id.headerText)
        val headerDate: TextView = view.findViewById(R.id.headerDate)
        val headerDiff: TextView = view.findViewById(R.id.headerDiff)
    }
}