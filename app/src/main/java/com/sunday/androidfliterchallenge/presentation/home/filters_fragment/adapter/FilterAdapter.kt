package com.sunday.androidfliterchallenge.presentation.home.filters_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunday.androidfliterchallenge.R
import com.sunday.androidfliterchallenge.data.entity.Filter

class FilterAdapter (
    var filters: List<Filter>,
    var listener: OnItemClickListener
) :
    RecyclerView.Adapter<FilterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_row_item, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        //holder.advice.setText(advises[position])
        val filter = filters[position]
        holder.bind(filter, listener)
    }

    override fun getItemCount(): Int {
        return filters.count()
    }

}