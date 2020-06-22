package com.sunday.androidfliterchallenge.data.remote

import com.sunday.androidfliterchallenge.data.entity.Filter
import io.reactivex.Observable
import retrofit2.http.GET

interface FilterService {

    @GET("accounts")
    fun getFilters() : Observable<List<Filter>>

}