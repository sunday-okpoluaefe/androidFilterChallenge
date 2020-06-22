package com.sunday.androidfliterchallenge.presentation.home.filters_fragment.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sunday.androidfliterchallenge.R
import com.sunday.androidfliterchallenge.data.entity.Filter

class FilterViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    var name: TextView = itemView.findViewById(R.id.name)
    var avatar: ImageView = itemView.findViewById(R.id.avatar)
    var gender: TextView = itemView.findViewById(R.id.gender)
    var colors: TextView = itemView.findViewById(R.id.color)
    var countries: TextView = itemView.findViewById(R.id.country)

    fun bind(filter: Filter, clickListener: OnItemClickListener)
    {

        name.text = filter.fullName
        gender.text = filter.gender
        countries.text = filter.countries?.joinToString(", ")
        colors.text = filter.colors?.joinToString(", ")

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            //.placeholder(R.drawable.progress_animation)
            //.error(R.drawable.progress_animation)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()
            //.override(96, 128)

        Glide.with(avatar.context).load(filter.avatar)
            .apply(options)
            .into(avatar)

        itemView.setOnClickListener {
            clickListener.onItemClicked(filter)
        }

    }

}
interface OnItemClickListener{
    fun onItemClicked(trip: Filter)
}