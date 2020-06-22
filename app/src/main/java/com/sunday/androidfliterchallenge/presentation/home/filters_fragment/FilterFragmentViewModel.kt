package com.sunday.androidfliterchallenge.presentation.home.filters_fragment

import androidx.lifecycle.ViewModel
import com.sunday.androidfliterchallenge.data.entity.Filter
import com.sunday.androidfliterchallenge.data.repository.FilterRepository
import javax.inject.Inject

class FilterFragmentViewModel @Inject constructor( val filterRepository: FilterRepository) : ViewModel() {

    fun getFilters() = filterRepository.getFilters()
    fun filtersObserver() = filterRepository.filtersObserver()

    fun getCarOwners(filter: Filter){

    }

    override fun onCleared() {
        super.onCleared()
        filterRepository.clear()
    }
}