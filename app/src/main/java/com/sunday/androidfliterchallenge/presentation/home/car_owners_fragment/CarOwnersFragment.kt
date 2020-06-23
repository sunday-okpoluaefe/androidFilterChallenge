package com.sunday.androidfliterchallenge.presentation.home.car_owners_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunday.androidfliterchallenge.R
import com.sunday.androidfliterchallenge.data.entity.CarOwner
import com.sunday.androidfliterchallenge.data.entity.Filter
import com.sunday.androidfliterchallenge.presentation.core.BaseFragment
import com.sunday.androidfliterchallenge.presentation.home.car_owners_fragment.adapter.CarOwnerAdapter
import com.sunday.androidfliterchallenge.presentation.home.car_owners_fragment.adapter.OnItemClickListener

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 * This is the car owners fragment .. to be implemented
 */
class CarOwnersFragment : BaseFragment() {

    lateinit var carOwnersList : ArrayList<CarOwner>

    lateinit var ownersRecyclerView: RecyclerView

    private fun filterCarOwners(data: ArrayList<CarOwner>, filter: Filter): List<CarOwner> {
       return data.filter { s ->
            (filter.colors!!.contains(s.carColor) ||
                    filter.countries!!.contains(s.country) ||
                    filter.fullName!!.contains(s.carModel!!) ||
                    filter.gender == s.gender || filter.fullName!!.contains(s.firstName!!))
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.filter_result_layout, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filter: Filter = arguments?.getParcelable("filter")!!
        carOwnersList = arguments?.getParcelableArrayList("owners")!!


        ownersRecyclerView = view.findViewById(R.id.list)
        ownersRecyclerView.setHasFixedSize(true)
        ownersRecyclerView.layoutManager = LinearLayoutManager(activity)

        val filterResult = filterCarOwners(carOwnersList, filter)
        activity?.title = "${resources.getString(R.string.filter_result_fragment_title)} -- ${filterResult.size} results"


        ownersRecyclerView.adapter = CarOwnerAdapter(filterResult, object : OnItemClickListener {
            override fun onItemClicked(owner: CarOwner) {


            }
        })


    }

}