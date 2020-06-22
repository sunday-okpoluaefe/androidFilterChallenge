package com.sunday.androidfliterchallenge.presentation.splash

import androidx.lifecycle.ViewModel
import com.sunday.androidfliterchallenge.data.entity.Filter
import com.sunday.androidfliterchallenge.data.repository.FilterRepository
import javax.inject.Inject

class SplashFragmentViewModel @Inject constructor(val filterRepository: FilterRepository) : ViewModel() {


    fun getFilters() = filterRepository.getFilters()
    fun filtersObserver() = filterRepository.filtersObserver()

    fun getCarOwners(filter: Filter){

    }

    override fun onCleared() {
        super.onCleared()
        filterRepository.clear()
    }

}