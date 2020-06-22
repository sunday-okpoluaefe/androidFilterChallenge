package com.sunday.androidfliterchallenge.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunday.androidfliterchallenge.data.entity.CarOwner
import com.sunday.androidfliterchallenge.data.entity.Filter
import com.sunday.androidfliterchallenge.data.remote.FilterService
import com.sunday.androidfliterchallenge.rx.Scheduler
import com.sunday.androidfliterchallenge.utils.Resource
import com.sunday.androidfliterchallenge.utils.Status
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

public class FilterRepositoryImpl @Inject constructor(
    val filterService: FilterService,
    val disposable: CompositeDisposable,
    val scheduler: Scheduler) : FilterRepository {

    private var filters  = MutableLiveData<Resource<List<Filter>>>()
    private var carOwners  = MutableLiveData<List<CarOwner>>()

    override fun getFilters() {
        /* notify listeners */
        filters.postValue(Resource(status = Status.LOADING))

        /* make network calls */
        val dis = filterService.getFilters()
            .subscribeOn(scheduler.background())
            .observeOn(scheduler.ui())
            .subscribe(
                {
                    filters.postValue(Resource(Status.SUCCESS, it, "success"))
                },
                {
                    filters.postValue(Resource(Status.SUCCESS, null, it.message))
                }
            )

        disposable.add(dis)
    }

    override fun filtersObserver(): LiveData<Resource<List<Filter>>> = filters

    override fun getCarOwners(filter: Filter) {
        TODO("Not yet implemented")
    }

    override fun carOwnersObserver(): LiveData<List<CarOwner>> = carOwners

    override fun clear() {
        disposable.clear()
    }


}