/*
 * YearRecyclerAdapter.kt
 *
 * Copyright 2018 by MicMun
 */
package de.micmun.android.deufeitage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import de.micmun.android.deufeitage.R
import de.micmun.android.deufeitage.model.YearItem


/**
 * Adapter for year selection.
 *
 * @author MicMun
 * @version 1.0, 07.08.18
 */
class YearRecyclerAdapter(private val myDataset: Array<YearItem>,
                          var listener: YearViewHolder.OnYearItemSelectedListener?) :
        RecyclerView.Adapter<YearViewHolder>(), YearViewHolder.OnYearItemSelectedListener {

    private var selectedItem: YearItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.year_text_view, parent,
                false) as CheckedTextView
        return YearViewHolder(textView, this)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        val mItem: YearItem = myDataset[position]
        holder.view.text = mItem.toString()

        holder.mItem = mItem
        holder.setChecked(mItem.isSelected)
    }

    override fun onYearItemSelected(item: YearItem) {
        if (selectedItem == item)
            return

        for (selectableItem in myDataset) {
            if (selectableItem != item && selectableItem.isSelected) {
                selectableItem.isSelected = false
            } else if (selectableItem == item && item.isSelected) {
                selectableItem.isSelected = true
                selectedItem = selectableItem
            }
        }
        notifyDataSetChanged()
        listener?.onYearItemSelected(item)
    }
}