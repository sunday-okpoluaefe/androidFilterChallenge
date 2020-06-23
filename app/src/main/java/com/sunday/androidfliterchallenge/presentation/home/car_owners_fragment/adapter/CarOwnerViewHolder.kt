package com.sunday.androidfliterchallenge.presentation.home.car_owners_fragment.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunday.androidfliterchallenge.R
import com.sunday.androidfliterchallenge.data.entity.CarOwner

class CarOwnerViewHolder (itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    var name: TextView = itemView.findViewById(R.id.name)
    var email: TextView = itemView.findViewById(R.id.email)
    var car: TextView = itemView.findViewById(R.id.car_info)
    var country: TextView = itemView.findViewById(R.id.country)
    var bio : TextView = itemView.findViewById(R.id.bio)
    var job : TextView = itemView.findViewById(R.id.job)

    @SuppressLint("SetTextI18n")
    fun bind(owner: CarOwner, clickListener: OnItemClickListener)
    {

        name.text = "${owner.firstName} ${owner.lastName}"
        email.text = owner.email
        car.text = "${owner.carColor} ${owner.carModel}, ${owner.carModelYear}"
        country.text = owner.country
        bio.text = owner.bio

        job.text = "${owner.jobTitle} - ${owner.gender}"

        itemView.setOnClickListener {
            clickListener.onItemClicked(owner)
        }

    }

}
interface OnItemClickListener{
    fun onItemClicked(owner: CarOwner)
}