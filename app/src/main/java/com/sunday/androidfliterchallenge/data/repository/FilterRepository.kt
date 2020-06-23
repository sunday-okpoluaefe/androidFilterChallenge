package com.sunday.androidfliterchallenge.data.repository

import androidx.lifecycle.LiveData
import com.sunday.androidfliterchallenge.data.entity.CarOwner
import com.sunday.androidfliterchallenge.data.entity.Filter
import com.sunday.androidfliterchallenge.utils.Resource

interface FilterRepository {

    fun getFilters()
    fun filtersObserver() : LiveData<Resource<ArrayList<Filter>>>

    fun getCarOwners(filter: Filter)
    fun carOwnersObserver() : LiveData<ArrayList<CarOwner>>


    fun clear()
}