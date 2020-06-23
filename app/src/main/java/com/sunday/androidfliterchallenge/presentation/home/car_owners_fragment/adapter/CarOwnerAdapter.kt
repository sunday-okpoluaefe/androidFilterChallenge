package com.sunday.androidfliterchallenge.presentation.home.car_owners_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunday.androidfliterchallenge.R
import com.sunday.androidfliterchallenge.data.entity.CarOwner

class CarOwnerAdapter (
    var owners: List<CarOwner>,
    var listener: OnItemClickListener
) :
    RecyclerView.Adapter<CarOwnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarOwnerViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_result_row_item, parent, false)
        return CarOwnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarOwnerViewHolder, position: Int) {
        //holder.advice.setText(advises[position])
        val owner = owners[position]
        holder.bind(owner, listener)
    }

    override fun getItemCount(): Int {
        return owners.count()
    }

}