package com.sunday.androidfliterchallenge.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunday.androidfliterchallenge.R
import com.sunday.androidfliterchallenge.data.entity.CarOwner
import com.sunday.androidfliterchallenge.data.entity.Filter
import com.sunday.androidfliterchallenge.data.remote.FilterService
import com.sunday.androidfliterchallenge.rx.Scheduler
import com.sunday.androidfliterchallenge.utils.Resource
import com.sunday.androidfliterchallenge.utils.Status
import io.reactivex.disposables.CompositeDisposable
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

public class FilterRepositoryImpl @Inject constructor(
    val filterService: FilterService,
    val disposable: CompositeDisposable,
    val scheduler: Scheduler) : FilterRepository {

    private var filters  = MutableLiveData<Resource<ArrayList<Filter>>>()
    private var carOwners  = MutableLiveData<Resource<ArrayList<CarOwner>>>()

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
                    filters.postValue(Resource(Status.ERROR, null, it.message))
                }
            )

        disposable.add(dis)
    }

    override fun filtersObserver(): LiveData<Resource<ArrayList<Filter>>> = filters

    /* Better approach -- inject application context -- to be improved */
    override fun getCarOwners(context: Context) {

        carOwners.postValue(Resource(status = Status.LOADING))

        val inputStream = context.resources.openRawResource(R.raw.car_ownsers_data)

        BufferedReader(
            InputStreamReader(inputStream, Charsets.UTF_8)
        )

        //this is all the file in the csv as a string, you can just write this to external storage
        val allText = inputStream.bufferedReader().use(BufferedReader::readText)

        val split = allText.split("\n")

        //the first row is the heading, so I split that off
        val heading = split.first()

        val owners = ArrayList<CarOwner>()
        for(data in split.subList(1, split.size)){
            val row = data.split(',')
            try{
                owners.add(CarOwner().apply {
                    firstName = row[1]
                    lastName  = row[2]
                    email  = row[3]
                    country  = row[4]
                    carModel  = row[5]
                    carModelYear  = row[6]
                    carColor  = row[7]
                    gender  = row[8]
                    jobTitle  = row[9]
                    bio   = row[10]
                })
            }catch (e: Exception){
                carOwners.postValue(Resource(status = Status.ERROR, message = e.localizedMessage))
            }
        }

        carOwners.postValue(Resource(status = Status.SUCCESS, data = owners))
    }


    override fun carOwnersObserver(): LiveData<Resource<ArrayList<CarOwner>>> = carOwners

    override fun clear() {
        disposable.clear()
    }



}