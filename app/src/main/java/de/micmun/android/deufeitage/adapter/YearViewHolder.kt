/*
 * YearViewHolder.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.adapter

import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import de.micmun.android.deufeitage.model.YearItem

/**
 * View Holder with textview for year selection.
 *
 * @author MicMun
 * @version 1.0, 06.07.18
 */
class YearViewHolder(val view: CheckedTextView, private val listener: OnYearItemSelectedListener) :
        RecyclerView.ViewHolder(view) {
    var mItem: YearItem? = null

    init {
        view.setOnClickListener {
            if (mItem!!.isSelected && !view.isActivated)
                setChecked(false)
            else if (!view.isActivated)
                setChecked(true)
            listener.onYearItemSelected(mItem as YearItem)
        }
    }

    fun setChecked(value: Boolean) {
        view.isActivated = value
        mItem!!.isSelected = value
    }

    /**
     * Interface for selected item.
     *
     * @author MicMun
     * @version 1.0, 06.07.18
     */
    interface OnYearItemSelectedListener {
        /**
         * Do something with the selected item.
         *
         * @param item selected item.
         */
        fun onYearItemSelected(item: YearItem)
    }
}